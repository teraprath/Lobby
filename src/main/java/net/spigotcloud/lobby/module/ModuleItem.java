package net.spigotcloud.lobby.module;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ModuleItem {

    private final Material material;
    private final String displayName;
    private final String[] lore;

    public ModuleItem(Material material, String displayName, String[] lore) {
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
    }

    public Material getType() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String[] getLore() {
        return lore;
    }

    public ItemStack get() {
        ItemStack itemStack = new ItemStack(this.material);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(this.displayName);
        meta.setLore(Arrays.asList(this.lore));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.values());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
