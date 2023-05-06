package dev.teraprath.lobby.module;

import dev.teraprath.lobby.Lobby;
import org.bukkit.plugin.InvalidPluginException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModuleManager {

    private static HashMap<String, IModule> modulesHashMap = new HashMap<>();

    private static ModuleLoader moduleLoader = new ModuleLoader();

    public ModuleManager() {
        File modules = new File(Lobby.getInstance().getDataFolder(), "modules");
        if (modules.isDirectory()) {
            for (File file : Objects.requireNonNull(modules.listFiles())) {
                if (file.getName().endsWith(".jar")) {
                    try {
                        ModuleManager.getModuleLoader().loadModule(file);
                    } catch (InvalidPluginException e) {
                        e.printStackTrace();
                    }
                }
            }
            for (Map.Entry<String, ModuleClassLoader> entry : ModuleManager.getModuleLoader().getModuleClassLoaderMap().entrySet()) {
                modulesHashMap.put(entry.getKey(), entry.getValue().getModule());
            }
        }
    }

    public void loadModules(){
        for (Map.Entry<String, IModule> entry : modulesHashMap.entrySet()) {
            entry.getValue().onLoad();
        }
    }

    public boolean moduleExist(String name){
        return modulesHashMap.containsKey(name);
    }

    public void loadModule(String name){
        modulesHashMap.get(name).onLoad();
        modulesHashMap.get(name).onEnable();
    }

    public void disableModule(String name){
        modulesHashMap.get(name).onDisable();

    }

    public void enableModules(){
        for (Map.Entry<String, IModule> entry : modulesHashMap.entrySet()) {
            entry.getValue().onEnable();
        }
    }

    public void disableModules(){
        for (Map.Entry<String, IModule> entry : modulesHashMap.entrySet()) {
            entry.getValue().onDisable();
        }
    }

    private static ModuleLoader getModuleLoader () {
        return moduleLoader;
    }
}