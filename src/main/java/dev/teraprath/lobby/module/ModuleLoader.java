package dev.teraprath.lobby.module;

import dev.teraprath.lobby.Lobby;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleLoader {

    private final ConcurrentHashMap<String, Class<?>> classes = new ConcurrentHashMap<>();
    private Map<String, ModuleClassLoader> moduleClassLoaderMap = new LinkedHashMap<>();

    void loadModule(File file) throws InvalidPluginException {
        // Validate.notNull(file, "File cannot be null");

        if (!file.exists()) {
            Lobby.getInstance().getLogger().severe("Trying to load " + file.getPath() + " module. An error occurs : " + "Module file does not exist.");
        } else {
            ModuleDescriptionFile description;
            try {
                description = this.getModuleDescriptionFile(file);
            } catch (InvalidDescriptionException e) {
                Lobby.getInstance().getLogger().severe("Trying to load " + file.getPath() + " module. An error occurs : " + "Invalid description");
                return;
            }

            ModuleClassLoader loader;
            try {
                loader = new ModuleClassLoader(this, this.getClass().getClassLoader(), description, file);
            } catch (Throwable e) {
                throw new InvalidPluginException(e);
            }

            if (moduleClassLoaderMap.get(description.getName()) != null) {
                Lobby.getInstance().getLogger().severe("Trying to load " + description.getName() + " module. An error occurs : " + "Can't have two module with the same name.");
            } else {
                moduleClassLoaderMap.put(description.getName(), loader);
            }
        }

    }

    void setClass(String name, Class<?> clazz) {
        if (!this.classes.containsKey(name)) {
            this.classes.put(name, clazz);
            if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.registerClass(serializable);
            }
        }
    }

    private ModuleDescriptionFile getModuleDescriptionFile(File file) throws InvalidDescriptionException {
        // Validate.notNull(file, "File cannot be null");
        JarFile jar = null;
        InputStream stream = null;

        ModuleDescriptionFile description;

        try {
            jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("module.yml");

            if (entry == null)
                throw new InvalidDescriptionException(new FileNotFoundException("Jar does not contain module.yml"));

            stream = jar.getInputStream(entry);
            description = new ModuleDescriptionFile(stream);

        } catch (IOException | YAMLException e) {
            throw new InvalidDescriptionException(e);
        } finally {
            if (jar != null) {
                try {
                    jar.close();
                } catch (IOException ignored) {
                }
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }

        return description;
    }


    Class<?> getClassByName(String name) {
        Class<?> cachedClass = this.classes.get(name);
        if (cachedClass != null) {
            return cachedClass;
        } else {
            for (String current : this.moduleClassLoaderMap.keySet()) {
                ModuleClassLoader loader = this.moduleClassLoaderMap.get(current);

                try {
                    cachedClass = loader.findClass(name, false);
                } catch (ClassNotFoundException ignored) {
                }

                if (cachedClass != null) {
                    return cachedClass;
                }
            }
            return null;
        }
    }

    public Map<String, ModuleClassLoader> getModuleClassLoaderMap() {
        return moduleClassLoaderMap;
    }

}
