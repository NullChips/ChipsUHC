package me.nullchips.chipsuhc.listeners.player;

import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Tommy on 14/02/2017.
 */
public class PlayerLeave implements Listener {

    ChatUtils cu = ChatUtils.getInstance();

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        e.setQuitMessage("");
        cu.broadcast(ChatColor.GREEN + e.getPlayer().getName() + ChatColor.WHITE + " has left the game.");

        //TODO Add time-out when in game.

    }

}
