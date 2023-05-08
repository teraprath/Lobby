package net.spigotcloud.lobby.module;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.event.HandlerList;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class ModuleLoader {

    private final Set<Module> modules = new HashSet<>();
    private final ModuleClassLoader classLoader = new ModuleClassLoader();

    public ModuleLoader() {
        classLoader.reload();
    }

    public void enable() {
        classLoader.getModuleClasses().forEach(((clazz, desc) -> {
            try {
                Object object = clazz.newInstance();

                if (object instanceof Module) {
                    Module module = (Module) object;
                    module.setDescriptionFile(desc);
                    module.onEnable();
                    module.getListeners().forEach(listener -> {
                        Lobby.getInstance().getServer().getPluginManager().registerEvents(listener, Lobby.getInstance());
                    });
                    module.getSubCommands().forEach(((s, subCommand) -> {
                        Lobby.getInstance().getBaseCommand().registerSubCommand(s, subCommand);
                    }));
                    modules.add(module);
                    Lobby.getInstance().getLogger().log(Level.INFO, "Enabled: Module " + desc.getName() + " v" + desc.getVersion() + " by " + desc.getAuthor());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }));
    }

    public void disable() {
        for (Module module : modules) {
            module.onDisable();
            module.getListeners().forEach(HandlerList::unregisterAll);
            module.getSubCommands().forEach(((s, subCommand) -> {
                Lobby.getInstance().getBaseCommand().unregister(s);
            }));
            Lobby.getInstance().getLogger().log(Level.INFO, "Disabled: Module " + module.getName() + " v" + module.getDescriptionFile().getVersion() + " by " + module.getDescriptionFile().getAuthor());

        }
    }

    public Set<Module> getModules() {
        return this.modules;
    }

    public void reload() {
        disable();
        classLoader.reload();
        enable();
    }


}
