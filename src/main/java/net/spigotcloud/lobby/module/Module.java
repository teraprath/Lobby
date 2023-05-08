package net.spigotcloud.lobby.module;

import net.spigotcloud.lobby.command.SubCommand;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Module {

    private final Set<Listener> listeners = new HashSet<>();
    private final Map<String, SubCommand> subCommands = new HashMap<>();
    private ModuleDescriptionFile descriptionFile;

    public abstract void onEnable();
    public abstract void onDisable();

    public String getName() {
        return getClass().getSimpleName();
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

    public Map<String, SubCommand> getSubCommands() {
        return this.subCommands;
    }

    public ModuleDescriptionFile getDescriptionFile() {
        return descriptionFile;
    }

    public void setDescriptionFile(@Nonnull ModuleDescriptionFile descriptionFile) {
        this.descriptionFile = descriptionFile;
    }

}
