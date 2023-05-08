package net.spigotcloud.lobby.listener;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        e.setJoinMessage(null);
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setExp(0);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        e.setQuitMessage(null);
        Lobby.getInstance().getBuildPlayers().remove(player);
    }

}
