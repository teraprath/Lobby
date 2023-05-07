package net.spigotcloud.lobby.module;

import net.spigotcloud.lobby.Lobby;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private List<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();
        reload();
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public void reload() {
        this.modules = ModuleHandler.loadAll(new File(Lobby.getInstance().getDataFolder() + "/modules"));
        for (Module module : modules) {
            module.getListeners().forEach(listener -> {
                Lobby.getInstance().getServer().getPluginManager().registerEvents(listener, Lobby.getInstance());
            });
            this.modules.add(module);
        }
    }

}
