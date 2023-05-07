package net.spigotcloud.lobby;

import net.spigotcloud.lobby.module.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lobby extends JavaPlugin {

    private static Lobby instance;
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {

        instance = this;

        this.moduleManager = new ModuleManager();
        this.moduleManager.reload();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Lobby getInstance() {
        return instance;
    }

}
