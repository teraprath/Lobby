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
        Lobby plugin = Lobby.getInstance();
        sender.sendMessage("");
        sender.sendMessage(plugin.getLanguageManager().getMessage("registered_modules").replace("%amount%", plugin.getModuleLoader().getModules().size() + ""));
        plugin.getModuleLoader().getModules().forEach(module -> {
            sender.sendMessage(plugin.getLanguageManager().getMessage("module_list").replace("%module%", module.getDescription().getName()).replace("%version%", module.getDescription().getVersion()).replace("%author%", module.getDescription().getAuthor()));
        });
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }

}

