package net.spigotcloud.lobby.command;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.List;

public class HelpCommand extends SubCommand {

    public HelpCommand() {
        super("help", "Show this page");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginDescriptionFile desc = Lobby.getInstance().getDescription();
        sender.sendMessage("");
        Lobby.getInstance().getBaseCommand().getSubCommands().forEach((cmd, subCommand) -> {
            sender.sendMessage(Lobby.getInstance().getLanguageManager().getMessage("help_list").replace("%command%", cmd).replace("%description%", subCommand.getDescription()).replace("%permission%", subCommand.getPermission()));
        });
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }

}
