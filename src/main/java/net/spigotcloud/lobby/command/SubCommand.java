package net.spigotcloud.lobby.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    private final String permission;
    private final String description;

    public SubCommand(String permission, String description) {
        this.permission = permission;
        this.description = description;
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);
    public abstract List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);

    public String getPermission() {
        return "lobby.command." + this.permission;
    }

    public String getDescription() {
        return description;
    }
}
