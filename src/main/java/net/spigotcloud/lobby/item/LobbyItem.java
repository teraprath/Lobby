package net.spigotcloud.lobby.item;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class LobbyItem {

    private ItemStack item;
    private ItemMeta meta;

    public LobbyItem(Material material) {
        item = new ItemStack(material);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.values());
    }

    public LobbyItem setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public LobbyItem setName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public LobbyItem setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public LobbyItem enchant(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public LobbyItem toUnbreakable() {
        meta.setUnbreakable(true);
        return this;
    }

    public LobbyItem dyeArmor(Color color) {
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) item.getItemMeta();
        assert armorMeta != null;
        armorMeta.setColor(color);
        meta = armorMeta;
        return this;
    }

    public LobbyItem toSkull(String skullOwner) {
        SkullMeta skullMeta = (SkullMeta) meta;
        assert skullMeta != null;
        OfflinePlayer owner = Bukkit.getOfflinePlayer(skullOwner);
        skullMeta.setOwningPlayer(owner);
        meta = skullMeta;
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
