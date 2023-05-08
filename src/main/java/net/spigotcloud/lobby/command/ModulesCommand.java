package net.spigotcloud.lobby.command;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.List;

public class ModulesCommand extends SubCommand {

    public ModulesCommand() {
        super("modules", "List all modules");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginDescriptionFile desc = Lobby.getInstance().getDescription();
        sender.sendMessage("");
        sender.sendMessage("§8│ §6§l" + desc.getName() + " v" + desc.getVersion());
        sender.sendMessage("§8│");
        sender.sendMessage("§8├ §7Installed modules (§e§l" + Lobby.getInstance().getModuleLoader().getModules().size() + "§7):");
        Lobby.getInstance().getModuleLoader().getModules().forEach(module -> {
            sender.sendMessage("§8├ §e" + module.getDescriptionFile().getName() + " v" + module.getDescriptionFile().getVersion() + " §7§oby " + module.getDescriptionFile().getAuthor());
        });
        sender.sendMessage("§8└");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }

}

