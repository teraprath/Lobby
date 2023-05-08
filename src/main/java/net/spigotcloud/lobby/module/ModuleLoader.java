package net.spigotcloud.lobby.module;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.zip.ZipFile;

public class ModuleLoader {

    private final Set<Module> modules = new HashSet<>();
    private final Map<Class<?>, ModuleDescriptionFile> classes = new HashMap();
    private final File file;

    public ModuleLoader() {

        this.file = new File(Lobby.getInstance().getDataFolder(), "modules");
        if (!file.exists()) {
            file.mkdir();
        }


    }


    public void load() {

        if (file.exists() && file.isDirectory()) {

            this.classes.clear();

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

                    String name = config.getString("name");
                    String author = config.getString("author");
                    String version = config.getString("version");

                    if (mainClass == null || name == null || version == null) {
                        Lobby.getInstance().getLogger().log(Level.SEVERE, "Error while loading module file " + jar.getName() + " (Please check 'module.yml' file in Module");
                        return;
                    }


                    ClassLoader l = URLClassLoader.newInstance(new URL[]{jar.toURI().toURL()}, getClass().getClassLoader());

                    Class<?> clazz = l.loadClass(mainClass);
                    classes.put(clazz, new ModuleDescriptionFile(name, author != null ? author : "Unknown", version));

                } catch (IOException e) {
                    Lobby.getInstance().getLogger().log(Level.SEVERE, "Error while loading module file " + jar.getName());
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    Lobby.getInstance().getLogger().log(Level.SEVERE, "Class not found! Wrong main defined in module.yml?: " + jar.getName() + " class: " + mainClass);
                    e.printStackTrace();
                }


            }


        }
    }

    public void enable() {
        this.classes.forEach((clazz, desc) -> {
            try {
                Object object = clazz.newInstance();

                if (object instanceof Module) {
                    Module module = (Module) object;
                    module.init(desc);
                    module.onEnable();
                    module.getListeners().forEach(listener -> {
                        Lobby.getInstance().getServer().getPluginManager().registerEvents(listener, Lobby.getInstance());
                    });
                    module.getSubCommands().forEach(((s, subCommand) -> {
                        Lobby.getInstance().getBaseCommand().registerSubCommand(s, subCommand);
                    }));
                    modules.add(module);
                    Lobby.getInstance().getLogger().log(Level.INFO, "Enabled: Module " + desc.getName() + " v" + desc.getVersion() + " by " + desc.getAuthor());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public void disable() {
        this.modules.clear();
        for (Module module : modules) {
            module.onDisable();
            module.getListeners().forEach(HandlerList::unregisterAll);
            module.getSubCommands().forEach(((s, subCommand) -> {
                Lobby.getInstance().getBaseCommand().unregister(s);
            }));
            Lobby.getInstance().getLogger().log(Level.INFO, "Disabled: Module " + module.getDescriptionFile().getName() + " v" + module.getDescriptionFile().getVersion() + " by " + module.getDescriptionFile().getAuthor());

        }
    }

    public Set<Module> getModules() {
        return this.modules;
    }

    public void reload() {
        disable();
        load();
        enable();
    }


}
