package me.nullchips.chipsuhc.listeners;

import me.nullchips.chipsuhc.utils.ChatUtils;
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

        //TODO Format chat with prefixes of teams etc.

    }

}
