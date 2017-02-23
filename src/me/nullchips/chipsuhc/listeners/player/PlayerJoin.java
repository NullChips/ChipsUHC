package me.nullchips.chipsuhc.listeners.player;

import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Tommy on 14/02/2017.
 */
public class PlayerJoin implements Listener {

    ChatUtils cu = ChatUtils.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        cu.broadcast(ChatColor.GREEN + e.getPlayer().getName() + ChatColor.WHITE + " has joined the game.");
    }

}
