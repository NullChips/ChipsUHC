package me.nullchips.chipsuhc.listeners.player;

import me.nullchips.chipsuhc.utils.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Tommy on 26/02/2017.
 */
public class PlayerDamage implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {

        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        if(GameState.isGameState(GameState.LOBBY) || GameState.isGameState(GameState.SPREAD) || GameState.isGameState(GameState.STARTING)) {
            e.setCancelled(true);
        }
    }

}
