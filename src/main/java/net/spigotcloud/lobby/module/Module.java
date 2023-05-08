package net.spigotcloud.lobby.module;

import net.spigotcloud.lobby.api.LobbyAPI;
import net.spigotcloud.lobby.command.SubCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Module {

    private final Set<Listener> listeners = new HashSet<>();
    private final Map<String, SubCommand> subCommands = new HashMap<>();
    private ModuleDescriptionFile descriptionFile;
    private File file;
    private FileConfiguration config;

    public void init(@Nonnull ModuleDescriptionFile moduleDescriptionFile) {
        this.descriptionFile = moduleDescriptionFile;
        this.file = new File(LobbyAPI.getPlugin().getDataFolder(), "modules/" + this.descriptionFile.getName());
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveConfig() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void onEnable();
    public abstract void onDisable();

    public void registerListener(@Nonnull Listener listener) {
        this.listeners.add(listener);
    }

    public Set<Listener> getListeners() {
        return this.listeners;
    }

    public void registerSubCommand(@Nonnull String command, @Nonnull SubCommand subCommand) {
        this.subCommands.put(command, subCommand);
    }

    public Map<String, SubCommand> getSubCommands() {
        return this.subCommands;
    }

    public ModuleDescriptionFile getDescriptionFile() {
        return descriptionFile;
    }

}
