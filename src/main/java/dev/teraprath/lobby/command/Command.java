package dev.teraprath.lobby.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.teraprath.lobby.Lobby;
import org.bukkit.plugin.PluginDescriptionFile;

public class Command {

    public static CommandAPICommand LOBBY = new CommandAPICommand("lobby")
            .withPermission("lobby.command")
            .withSubcommand(new CommandAPICommand("reload")
                    .withPermission("lobby.command.reload")
                    .executes(((sender, args) -> {
                        Lobby.getManager().disableModules();
                        Lobby.getManager().enableModules();
                    }))
            ).executes(((sender, args) -> {
                PluginDescriptionFile desc = Lobby.getInstance().getDescription();
                sender.sendMessage(desc.getName() + "_" + desc.getVersion());
                sender.sendMessage("Author:"  + desc.getAuthors().get(0) + "[" + desc.getWebsite() + "]");
            }));

}
