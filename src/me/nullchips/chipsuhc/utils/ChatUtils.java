package me.nullchips.chipsuhc.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 13/02/2017.
 */
public class ChatUtils {

    private ChatUtils() { }
    private static ChatUtils instance;

    private SettingsManager sm = SettingsManager.getInstance();

    private String prefix;

    public static ChatUtils getInstance() {
        if(instance == null) {
            instance = new ChatUtils();
        }
        return instance;
    }


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void message(Player p, String message) {
        p.sendMessage(prefix + " " + ChatColor.WHITE + message);
    }
}
