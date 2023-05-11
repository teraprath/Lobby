package net.spigotcloud.lobby.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class FileManager {

    private final File file;
    private final FileConfiguration configuration;

    public FileManager(File file) {
        this.file = file;
        this.configuration = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            getDefaults().forEach(((s, o) -> {
                getConfiguration().set(s, o);
            }));
            save();
        }
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract HashMap<String, Object> getDefaults();
    public abstract void reload();

}
