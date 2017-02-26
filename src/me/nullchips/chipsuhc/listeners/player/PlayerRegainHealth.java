package me.nullchips.chipsuhc.listeners.player;

import me.nullchips.chipsuhc.game.GameCore;
import me.nullchips.chipsuhc.utils.GameState;
import me.nullchips.chipsuhc.utils.TeamUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

/**
 * Created by Tommy on 14/02/2017.
 */
public class PlayerRegainHealth implements Listener {

    GameCore gc = GameCore.getInstance();

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent e) {

        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();

        if(GameState.isGameState(GameState.LOBBY) || GameState.isGameState(GameState.STARTING) || GameState.isGameState(GameState.SPREAD)) {
            return;
        }

        if(!gc.getNotPlaying().contains(p.getUniqueId().toString())) {
            e.setCancelled(true);
        }
    }

}
