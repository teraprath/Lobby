package net.spigotcloud.lobby.manager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager extends FileManager {

    private final Map<String, String> messages;
    private String prefix;
    private String noPlayerInstance;
    private String wrongUsage;
    private String noPermission;

    public LanguageManager(File file) {
        super(file);
        this.messages = new HashMap<>();
    }

    @Override
    public HashMap<String, Object> getDefaults() {
        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("messages.spawn_set", "&7Location &fSpawn &7has been set.");
        defaults.put("messages.reload", "&7Modules and configurations has been reloaded.");
        defaults.put("messages.build_mode_on", "&7Build Mode: &fOn");
        defaults.put("messages.build_mode_off", "&7Build Mode: &cOff");
        defaults.put("messages.help_list", "&f/%command% &7&o%description% &8(&c%permission%&8)");
        defaults.put("messages.registered_modules", "&fRegistered modules &7(%amount%):");
        defaults.put("messages.module_list", "&f%module% v%version% &7&oby %author%");
        defaults.put("general.no_player_instance", "&cConsole cannot execute player commands.");
        defaults.put("general.wrong_usage", "&7Please use: &f%usage%");
        defaults.put("general.no_permission", "&7Permission &c%permission% &7required to do this.");
        defaults.put("general.prefix", "&7[&fLobby&7] ");
        return defaults;
    }

    @Override
    public void reload() {

        this.prefix = getConfiguration().getString("general.prefix");
        this.noPlayerInstance = getConfiguration().getString("general.no_player_instance");
        this.wrongUsage = getConfiguration().getString("general.wrong_usage");
        this.noPermission = getConfiguration().getString("general.no_permission");

        ConfigurationSection section = getConfiguration().getConfigurationSection("messages");
        if (section != null) {
            this.messages.clear();
            for (String key : section.getKeys(false)) {
                this.messages.put(key, section.getString(key));
            }
        }

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
        return format(prefix + this.messages.get(key));
    }

    private String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
