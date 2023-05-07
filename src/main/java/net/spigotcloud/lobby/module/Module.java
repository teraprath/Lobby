package net.spigotcloud.lobby.module;

import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public abstract class Module {

    private final Set<Listener> listeners = new HashSet<>();

    public abstract void onEnable();
    public abstract void onDisable();

    public void addListener(@Nonnull Listener listener) {
        this.listeners.add(listener);
    }

    public Set<Listener> getListeners() {
        return this.listeners;
    }

}
