package net.spigotcloud.lobby.module;

import net.spigotcloud.lobby.command.SubCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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
    private final ModuleItem item;
    private File dataFolder;
    private File file;
    private FileConfiguration config;

    public Module(ModuleItem moduleItem) {
        this.item = moduleItem;
    }


    public void init(@Nonnull ModuleDescriptionFile moduleDescriptionFile) {
        this.descriptionFile = moduleDescriptionFile;
        this.dataFolder = new File("plugins/Lobby/modules/" + this.descriptionFile.getName());
        this.file = new File(this.dataFolder + File.separator + "config.yml");
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

    public void registerListener(@Nonnull Listener listener) {
        this.listeners.add(listener);
    }

    public Set<Listener> getListeners() {
        return this.listeners;
    }

    public void registerSubCommand(@Nonnull String command, @Nonnull SubCommand subCommand) {
        this.subCommands.put(command, subCommand);
    }

    public ModuleItem getItem() {
        return this.item;
    }

    public Map<String, SubCommand> getSubCommands() {
        return this.subCommands;
    }

    public ModuleDescriptionFile getDescriptionFile() {
        return descriptionFile;
    }

    public File getDataFolder() {
        return this.dataFolder;
    }

    public abstract void onEnable();
    public abstract void onDisable();
    public abstract void onItemClick(PlayerInteractEvent event);

}
