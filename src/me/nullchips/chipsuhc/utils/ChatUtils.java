package me.nullchips.chipsuhc.utils;

import org.bukkit.Bukkit;
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
    private String consoleMessage = ChatColor.RED + "The console cannot perform this command.";

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

    public void broadcast(String message) {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(this.prefix + " " + message);
        }
    }

    public void noPermission(Player p) {
        p.sendMessage(ChatColor.RED + "You don't have permission to perform this action.");
    }

    public String getConsoleMessage() {
        return consoleMessage;
    }

    public void setConsoleMessage(String consoleMessage) {
        this.consoleMessage = consoleMessage;
    }
}
