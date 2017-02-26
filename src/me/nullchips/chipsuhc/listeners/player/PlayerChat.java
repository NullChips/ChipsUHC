package me.nullchips.chipsuhc.listeners.player;

import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.TeamUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Tommy on 16/02/2017.
 */
public class PlayerChat implements Listener {

    ChatUtils cu = ChatUtils.getInstance();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        if(cu.isMuteAll() && !(p.hasPermission("uhc.bypassglobalmute"))) {
            e.setCancelled(true);
            return;
        }

        if(cu.isMuted(p) && !(p.hasPermission("uhc.bypassmute"))) {
            e.setCancelled(true);
            return;
        }

        if(p.hasPermission("uhc.hostchat")) {
            e.setFormat(ChatColor.RED + "" + ChatColor.BOLD + "[" + ChatColor.AQUA + ChatColor.BOLD + "Host" + ChatColor.RED + ChatColor.BOLD + "] "
                    + ChatColor.GREEN + "%s" + ChatColor.GOLD + " >> " + ChatColor.WHITE + "%s");
            return;
        }

        e.setFormat(ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GOLD + " >> " + ChatColor.WHITE + "%s");

    }

}
