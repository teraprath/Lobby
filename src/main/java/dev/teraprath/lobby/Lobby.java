package dev.teraprath.lobby;

import dev.teraprath.lobby.module.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lobby extends JavaPlugin {

    private static Lobby instance;
    private static ModuleManager manager;

    @Override
    public void onLoad() {

        instance = this;
        manager = new ModuleManager();

    }

    public static void registerListener(Listener listener){
        if (instance != null) {
            Bukkit.getPluginManager().registerEvents(listener,getInstance());
        }
    }


    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        manager.disableModules();
    }

    public static Lobby getInstance() {
        return instance;
    }

    public static ModuleManager getManager() {
        return manager;
    }

}
