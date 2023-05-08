package net.spigotcloud.lobby;

import net.spigotcloud.lobby.command.*;
import net.spigotcloud.lobby.listener.ItemListener;
import net.spigotcloud.lobby.listener.JoinQuitListener;
import net.spigotcloud.lobby.listener.ProtectListener;
import net.spigotcloud.lobby.manager.LanguageManager;
import net.spigotcloud.lobby.manager.LocationManager;
import net.spigotcloud.lobby.module.ModuleLoader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class Lobby extends JavaPlugin {

    private static Lobby instance;
    private final BaseCommand command = new BaseCommand();
    private final Set<Player> buildPlayers = new HashSet<>();

    private ModuleLoader moduleLoader;
    private LanguageManager languageManager;
    private LocationManager locationManager;

    @Override
    public void onEnable() {

        instance = this;

        this.moduleLoader = new ModuleLoader();

        this.languageManager = new LanguageManager(new File(getDataFolder(), "language.yml"));
        this.locationManager = new LocationManager(new File(getDataFolder(), "locations.yml"));

        registerCommands();
        registerEvents();


        moduleLoader.reload();

        log();
    }

    @Override
    public void onDisable() {
        moduleLoader.disable();
    }

    private void registerCommands() {
        getCommand("lobby").setExecutor(this.command);
        command.registerSubCommand("help", new HelpCommand());
        command.registerSubCommand("reload", new ReloadCommand());
        command.registerSubCommand("modules", new ModulesCommand());
        command.registerSubCommand("build", new BuildCommand());
        command.registerSubCommand("setspawn", new SetSpawnCommand());
    }

    private void registerEvents() {
        final PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ItemListener(), this);
        pm.registerEvents(new JoinQuitListener(), this);
        pm.registerEvents(new ProtectListener(), this);
    }


    private void log() {
        getLogger().info("Registered " + moduleLoader.getModules().size() + " modules.");
        getLogger().info("Registered " + getBaseCommand().getSubCommands().size() + " commands.");
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

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public static Lobby getInstance() {
        return instance;
    }


}
