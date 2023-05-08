package net.spigotcloud.lobby.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LobbyAPI {

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Lobby");
    }

}
