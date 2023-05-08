package net.spigotcloud.lobby.command;

// TODO: Custom messages

import net.spigotcloud.lobby.Lobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ModulesCommand extends SubCommand {

    public ModulesCommand() {
        super("modules");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("");
        sender.sendMessage("§6§lLobby Modules");
        Lobby.getInstance().getModuleLoader().getModules().forEach(module -> {
            sender.sendMessage("§8- §7" + module.getName() + " v" + module.getDescriptionFile().getVersion() + " by " + module.getDescriptionFile().getAuthor());
        });
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }

}

