package net.spigotcloud.lobby.listener;

import net.spigotcloud.lobby.Lobby;
import net.spigotcloud.lobby.handler.PlayerHandler;
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
        PlayerHandler.reset(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        e.setQuitMessage(null);
        Lobby.getInstance().getBuildPlayers().remove(player);
    }

}
