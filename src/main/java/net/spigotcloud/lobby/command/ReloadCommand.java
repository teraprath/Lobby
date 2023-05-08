package net.spigotcloud.lobby.command;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends SubCommand {

    public ReloadCommand() {
        super("reload", "Reloads the plugin");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Lobby.getInstance().getModuleLoader().reload();
        Lobby.getInstance().getLocationManager().reload();
        Lobby.getInstance().getLanguageManager().reload();
        sender.sendMessage(Lobby.getInstance().getLanguageManager().getMessage("reload"));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }

}
