package dev.teraprath.lobby.module;

public interface IModule {

    void onLoad();

    void onEnable();

    void onDisable();

    boolean getEnabled();
}
