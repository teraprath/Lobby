package net.spigotcloud.lobby.module;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public abstract class ModuleItem implements Listener {

    private final ItemStack itemStack;
    private final String displayName;

    public ModuleItem(@Nonnull Material material, @Nonnull String displayName) {

        this.itemStack = new ItemStack(material);
        this.displayName = displayName;
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(displayName);
        meta.setUnbreakable(true);
        meta.removeItemFlags(ItemFlag.values());
        this.itemStack.setItemMeta(meta);

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getItem() != null && e.getItem().getType().equals(itemStack.getType())) {
            ItemMeta meta = e.getItem().getItemMeta();
            assert meta != null;
            if (meta.getDisplayName().equals(this.displayName)) {

                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    onClick();
                    if (e.getPlayer().isSneaking()) {
                        onShiftRightClick();
                        onShiftClick();
                    } else {
                        onRightClick();
                    }
                } else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_AIR)) {
                    onClick();
                    if (e.getPlayer().isSneaking()) {
                        onShiftLeftClick();
                        onShiftClick();
                    } else {
                        onLeftClick();
                    }
                }


            }
        }

    }

    public abstract void onClick();
    public abstract void onShiftClick();
    public abstract void onLeftClick();
    public abstract void onShiftLeftClick();
    public abstract void onRightClick();
    public abstract void onShiftRightClick();

    public ItemStack getItemStack() {
        return this.itemStack;
    }



}
