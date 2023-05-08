package net.spigotcloud.lobby.command;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BuildCommand extends SubCommand {

    public BuildCommand() {
        super("build", "Toggle build mode");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lobby.getInstance().getLanguageManager().getNoPlayerInstance());
            return false;
        }

        final Player player = (Player) sender;

        if (Lobby.getInstance().getBuildPlayers().contains(player)) {
            Lobby.getInstance().getBuildPlayers().remove(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(Lobby.getInstance().getLanguageManager().getMessage("build_mode_on"));
        } else {
            Lobby.getInstance().getBuildPlayers().add(player);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(Lobby.getInstance().getLanguageManager().getMessage("build_mode_off"));
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
