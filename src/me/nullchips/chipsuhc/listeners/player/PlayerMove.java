package me.nullchips.chipsuhc.listeners.player;

import me.nullchips.chipsuhc.utils.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Tommy on 23/02/2017.
 */
public class PlayerMove implements Listener {

    PlayerUtils pu = PlayerUtils.getInstance();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(pu.isAllFrozen() || pu.isFrozen(e.getPlayer())) {
            if(!e.getPlayer().getUniqueId().toString().equalsIgnoreCase(pu.getFreezeAllSender())) {
                Location from = e.getFrom();
                Location to = e.getTo();
                double x = Math.floor(from.getX());
                double z = Math.floor(from.getZ());
                if (Math.floor(to.getX()) != x || Math.floor(to.getZ()) != z) {
                    x += .5;
                    z += .5;
                    e.getPlayer().teleport(new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
                }
            }
        }
    }

}
