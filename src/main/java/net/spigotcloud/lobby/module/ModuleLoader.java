package net.spigotcloud.lobby.module;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.zip.ZipFile;

public class ModuleLoader {

    private final Set<Module> modules = new HashSet<>();
    private final Set<Class<?>> moduleClasses = new HashSet<>();
    private final File file;


    public ModuleLoader() {

        this.file = new File(Lobby.getInstance().getDataFolder() + "/modules");
        if (!file.exists()) { file.mkdir(); }

    }

    public void reload() {

        if (file.exists() && file.isDirectory()) {

            File[] files = file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jar");
                }
            });

            for (File jar : files) {
                String mainClass = null;
                try {
                    ZipFile zipFile = new ZipFile(jar);

                    InputStream is = zipFile.getInputStream(zipFile.getEntry("module.yml"));

                    YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(is));
                    mainClass = config.getString("main");

                    ClassLoader l = URLClassLoader.newInstance(new URL[]{jar.toURI().toURL()}, getClass().getClassLoader());

                    Class<?> clazz = l.loadClass(mainClass);
                    moduleClasses.add(clazz);

                } catch (IOException e) {
                    Lobby.getInstance().getLogger().log(Level.SEVERE, "Error while loading module file " + jar.getName());
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    Lobby.getInstance().getLogger().log(Level.SEVERE, "Class not found! Wrong main defined in extension.yml?: " + jar.getName() + " class: " + mainClass);
                    e.printStackTrace();
                }


            }
        }
    }

    public void enable() {
        for (Class<?> clazz : moduleClasses) {

            try {
                Object object = clazz.newInstance();

                if (object instanceof Module) {
                    Module module = (Module) object;
                    module.getListeners().forEach(listener -> {
                        Lobby.getInstance().getServer().getPluginManager().registerEvents(listener, Lobby.getInstance());
                    });
                    module.onEnable();
                    modules.add(module);
                    Lobby.getInstance().getLogger().log(Level.INFO, "Module " + module.getName() + " enabled!");
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void disable() {
        for (Module module : modules) {
            module.getListeners().forEach(HandlerList::unregisterAll);
            module.onDisable();
            Lobby.getInstance().getLogger().log(Level.INFO, "Module " + module.getName() + " disabled!");
        }
    }


}
