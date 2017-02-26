package me.nullchips.chipsuhc.listeners.block;

import me.nullchips.chipsuhc.utils.GameState;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Tommy on 26/02/2017.
 */
public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(GameState.isGameState(GameState.LOBBY) || GameState.isGameState(GameState.SPREAD) || GameState.isGameState(GameState.STARTING)) {
            if(!e.getPlayer().hasPermission("uhc.breakblock")) {
                e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to break blocks.");
                e.setCancelled(true);
            }
        }
    }


}
