package net.spigotcloud.lobby.listener;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemListener implements Listener {

    @EventHandler
    public void onItem(PlayerInteractEvent e) {
        if (e.getItem() != null && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            Lobby.getInstance().getModuleLoader().getModules().forEach(module -> {
                if (module.getItem() != null && e.getItem() == module.getItem()) {
                    module.onItemClick(e);
                }
            });
        }
    }

}
