package net.spigotcloud.lobby.handler;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class PlayerHandler {

    public static void reset(@Nonnull Player player) {
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setExp(0);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setGameMode(GameMode.SURVIVAL);
        Lobby.getInstance().getModuleLoader().getModules().forEach(module -> {
            if (module.getItem() != null) {
                player.getInventory().setItem(module.getItem().getSlot(), module.getItem().get());
            }
        });
    }

}
