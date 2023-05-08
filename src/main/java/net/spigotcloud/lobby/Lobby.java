package net.spigotcloud.lobby;

import net.spigotcloud.lobby.module.ModuleLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lobby extends JavaPlugin {

    private static Lobby instance;
    private ModuleLoader moduleLoader;

    @Override
    public void onEnable() {

        instance = this;

        this.moduleLoader = new ModuleLoader();

        moduleLoader.reload();
        moduleLoader.enable();

    }

    @Override
    public void onDisable() {
        moduleLoader.disable();
    }

    public ModuleLoader getModuleLoader() {
        return this.moduleLoader;
    }

    public static Lobby getInstance() {
        return instance;
    }

}
