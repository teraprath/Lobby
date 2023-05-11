package net.spigotcloud.lobby.listener;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemListener implements Listener {

    @EventHandler
    public void onItem(PlayerInteractEvent e) {

        final Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || e.getItem() == null) {
            return;
        }

        e.setCancelled(true);

        Lobby.getInstance().getModuleLoader().getModules().forEach(module -> {
            if (module.getItem() != null && e.getItem().equals(module.getItem().get())) {
                module.onItemClick(e);
            }
        });

    }

}
