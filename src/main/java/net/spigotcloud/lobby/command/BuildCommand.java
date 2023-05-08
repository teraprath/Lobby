package net.spigotcloud.lobby.command;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

// TODO: Custom messages

public class BuildCommand extends SubCommand {

    public BuildCommand() {
        super("build");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            // TODO: Send message to console
            return false;
        }

        final Player player = (Player) sender;

        if (Lobby.getInstance().getBuildPlayers().contains(player)) {
            Lobby.getInstance().getBuildPlayers().remove(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage("Build Mode: off");
        } else {
            Lobby.getInstance().getBuildPlayers().add(player);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage("Build Mode: on");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
