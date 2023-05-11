package net.spigotcloud.lobby.module;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;

public class ModuleItem {

    private final ItemStack itemStack;
    private final int slot;

    public ModuleItem(@Nonnull ItemStack itemStack, @Nonnegative int defaultSlot) {
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.values());
        itemStack.setItemMeta(meta);
        this.itemStack = itemStack;
        this.slot = defaultSlot;
    }

    public ModuleItem setName(@Nonnull String displayName) {
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ModuleItem setLore(@Nonnull String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStack get() {
        return this.itemStack;
    }

    public int getSlot() {
        return this.slot;
    }

}
