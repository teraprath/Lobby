package net.spigotcloud.lobby;

import net.spigotcloud.lobby.api.LobbyAPI;
import net.spigotcloud.lobby.command.BaseCommand;
import net.spigotcloud.lobby.command.BuildCommand;
import net.spigotcloud.lobby.command.ModulesCommand;
import net.spigotcloud.lobby.command.ReloadCommand;
import net.spigotcloud.lobby.listener.JoinQuitListener;
import net.spigotcloud.lobby.listener.ProtectListener;
import net.spigotcloud.lobby.module.ModuleLoader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class Lobby extends JavaPlugin {

    private static Lobby instance;
    private final BaseCommand command = new BaseCommand();
    private final Set<Player> buildPlayers = new HashSet<>();
    private ModuleLoader moduleLoader;

    @Override
    public void onEnable() {

        instance = this;

        this.moduleLoader = new ModuleLoader();

        moduleLoader.reload();
        moduleLoader.enable();

        registerCommands();
        registerEvents();


    }

    private void registerCommands() {
        getCommand("lobby").setExecutor(this.command);
        command.registerSubCommand("reload", new ReloadCommand());
        command.registerSubCommand("modules", new ModulesCommand());
        command.registerSubCommand("build", new BuildCommand());
    }

    private void registerEvents() {
        final PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinQuitListener(), this);
        pm.registerEvents(new ProtectListener(), this);
    }

    @Override
    public void onDisable() {
        moduleLoader.disable();
    }

    public BaseCommand getBaseCommand() {
        return this.command;
    }


    public ModuleLoader getModuleLoader() {
        return this.moduleLoader;
    }

    public Set<Player> getBuildPlayers() {
        return this.buildPlayers;
    }

    public static Lobby getInstance() {
        return instance;
    }


}
