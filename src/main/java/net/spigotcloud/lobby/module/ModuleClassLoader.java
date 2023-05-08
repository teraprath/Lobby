package net.spigotcloud.lobby.module;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.zip.ZipFile;

public class ModuleClassLoader {

    private final Map<Class<?>, ModuleDescriptionFile> moduleClasses = new HashMap<>();
    private final File file;

    public ModuleClassLoader() {

        File dataFolder = Lobby.getInstance().getDataFolder();

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        this.file = new File(dataFolder + File.separator + "modules");

        if (!this.file.exists()) {
            dataFolder.mkdir();
        }

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

                    ModuleDescriptionFile descriptionFile = new ModuleDescriptionFile(config.getString("name"), config.getString("author"), config.getString("version"));

                    ClassLoader l = URLClassLoader.newInstance(new URL[]{jar.toURI().toURL()}, getClass().getClassLoader());

                    Class<?> clazz = l.loadClass(mainClass);
                    moduleClasses.put(clazz, descriptionFile);

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

    public Map<Class<?>, ModuleDescriptionFile> getModuleClasses() {
        return this.moduleClasses;
    }



}
