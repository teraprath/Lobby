package net.spigotcloud.lobby.manager;

import org.bukkit.Location;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;

public class LocationManager extends FileManager {

    private Location spawn;


    public LocationManager(File file) {
        super(file);
    }

    @Override
    public HashMap<String, Object> getDefaults() {
        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("spawn", null);
        return defaults;
    }

    @Override
    public void reload() {
        this.spawn = getConfiguration().getLocation("spawn");
    }

    public void setSpawn(@Nonnull Location location) {
        getConfiguration().set("spawn", location);
        save();
    }

    public Location getSpawn() {
        return this.spawn;
    }

}
