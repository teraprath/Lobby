package dev.teraprath.lobby;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.teraprath.lobby.command.Command;
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

        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(false));
        Command.LOBBY.register();

    }

    public static void registerListener(Listener listener){
        if (instance != null) {
            Bukkit.getPluginManager().registerEvents(listener,getInstance());
        }
    }


    @Override
    public void onEnable() {
        CommandAPI.onEnable();
    }

    @Override
    public void onDisable() {
        manager.disableModules();
        CommandAPI.onDisable();
    }

    public static Lobby getInstance() {
        return instance;
    }

    public static ModuleManager getManager() {
        return manager;
    }

}
