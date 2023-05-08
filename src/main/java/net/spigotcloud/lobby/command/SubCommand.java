package net.spigotcloud.lobby.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    private final String permission;

    public SubCommand(String permission) {
        this.permission = permission;
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);
    public abstract List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);

    public String getPermission() {
        return "lobby.command." + this.permission;
    }

}
