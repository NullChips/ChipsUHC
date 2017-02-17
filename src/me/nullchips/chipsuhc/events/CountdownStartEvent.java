package me.nullchips.chipsuhc.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Tommy on 17/02/2017.
 */
public class CountdownStartEvent extends Event {

    private Player player;

    public CountdownStartEvent(Player p) {
        this.player = p;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }
}
