package net.spigotcloud.lobby.manager;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager extends FileManager{

    private final Map<ItemStack, Integer> items;

    public InventoryManager(File file) {
        super(file);
        this.items = new HashMap<>();
    }

    @Override
    public HashMap<String, Object> getDefaults() {
        HashMap<String, Object> defaults = new HashMap<>();
        Lobby.getInstance().getModuleLoader().getModules().forEach(module -> {
            if (module.getItem() != null && getConfiguration().get(module.getDescription().getName()) == null) {
                defaults.put(module.getDescription().getName(), module.getItem().getSlot());
            }
        });
        return defaults;
    }

    @Override
    public void reload() {
        this.items.clear();
        Lobby.getInstance().getModuleLoader().getModules().forEach(module -> {
            if (module.getItem() != null && getConfiguration().get(module.getDescription().getName()) != null) {
                this.items.put(module.getItem().get(), getConfiguration().getInt( module.getDescription().getName()));
            }
        });
    }

    public Map<ItemStack, Integer> getItems() {
        return items;
    }

}
