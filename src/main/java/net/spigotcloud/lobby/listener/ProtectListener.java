package net.spigotcloud.lobby.listener;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import javax.annotation.Nonnull;

public class ProtectListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        protectEvent(e, true, e.getPlayer());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        protectEvent(e, true, e.getPlayer());
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        protectEvent(e, true, e.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        protectEvent(e, true, (Player) e.getWhoClicked());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        protectEvent(e, true, e.getPlayer());
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        protectEvent(e, false, null);
    }

    @EventHandler
    public void onItemSwap(PlayerSwapHandItemsEvent e) {
        protectEvent(e, true, e.getPlayer());
    }

    private void protectEvent(@Nonnull Cancellable event, boolean bypass, Player player) {
        if (!Lobby.getInstance().getBuildPlayers().contains(player)) {
            event.setCancelled(true);
        }
    }

}
