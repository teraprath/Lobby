package net.spigotcloud.lobby.manager;

import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;

public class LanguageManager extends FileManager {

    private String prefix;
    private String noPlayerInstance;
    private String wrongUsage;
    private String noPermission;

    public LanguageManager(File file) {
        super(file);
    }

    @Override
    public HashMap<String, Object> getDefaults() {
        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("prefix", "&8[&6Lobby&8] &7");
        defaults.put("no_player_instance", "&cConsole cannot execute player commands.");
        defaults.put("wrong_usage", "&7Please use: &e%usage%");
        defaults.put("no_permission", "&7Permission &c%permission% &7required to do this.");
        defaults.put("spawn_set", "&7Location &eSpawn &7has been set.");
        defaults.put("reload", "&7Plugin successfully reloaded.");
        defaults.put("build_mode_on", "&7Build Mode: &eon");
        defaults.put("build_mode_off", "&7Build Mode: &coff");
        defaults.put("help_list", "&e%command% &8- &7&o%description% &8(&c%permission%&8)");
        return defaults;
    }

    @Override
    public void reload() {
        this.prefix = getConfiguration().getString("prefix");
        this.noPlayerInstance = getConfiguration().getString("no_player_instance");
        this.wrongUsage = getConfiguration().getString("wrong_usage");
        this.noPermission = getConfiguration().getString("no_permission");
    }

    public String getPrefix() {
        return format(prefix);
    }

    public String getNoPlayerInstance() {
        return format(prefix + noPlayerInstance);
    }

    public String getWrongUsage(@Nonnull String usage) {
        return format(prefix + wrongUsage).replace("%usage%", "/lobby " + usage);
    }

    public String getNoPermission(@Nonnull String permission) {
        return format(prefix + noPermission).replace("%permission%", permission);
    }

    public String getMessage(@Nonnull String key) {
        return format(prefix + getConfiguration().getString(key));
    }

    private String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
