package net.spigotcloud.lobby.command;

import net.spigotcloud.lobby.Lobby;
import net.spigotcloud.lobby.manager.LanguageManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetSpawnCommand extends SubCommand {

    public SetSpawnCommand() {
        super("setspawn", "Set spawn location");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        LanguageManager languageManager = Lobby.getInstance().getLanguageManager();

        if (!(sender instanceof Player)) {
            sender.sendMessage(languageManager.getNoPlayerInstance());
            return false;
        }

        final Player player = (Player) sender;
        final Location location = player.getLocation();
        Lobby.getInstance().getLocationManager().setSpawn(location);
        player.sendMessage(languageManager.getMessage("spawn_set"));

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }

}
