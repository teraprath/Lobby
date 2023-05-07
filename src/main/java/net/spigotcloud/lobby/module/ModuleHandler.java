package net.spigotcloud.lobby.module;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleHandler {

    public static List<Module> loadAll(File dir) {
        if(!dir.isDirectory()) {
            return Collections.emptyList();
        }
        if(!dir.exists()) {
            return Collections.emptyList();
        }
        List<Module> loaded = new ArrayList<>();
        ModuleLoader loader = new ModuleLoader();
        for(File file : dir.listFiles((file, name) -> name.endsWith(".jar"))) {
            loaded.add(loader.load(file, Module.class));
        }
        return loaded;
    }


}
