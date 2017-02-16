package me.nullchips.chipsuhc.utils;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Tommy on 13/02/2017.
 */
public class ChatUtils {

    private ChatUtils() { }
    private static ChatUtils instance;

    private SettingsManager sm = SettingsManager.getInstance();

    private String prefix;

    private String consoleMessage = ChatColor.RED + "The console cannot perform this command.";
    private boolean muteAll = false;
    private ArrayList<String> mutedPlayers = new ArrayList<String>();

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

    public boolean isInteger(String s) {
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }

    public String convertItemName(Material m) {

        String itemName = m.toString();

        String[] parts = itemName.split("_");

        String finalName = "";

        for (String s : parts) {
            finalName = (finalName + s + " ");
        }

        finalName = WordUtils.capitalizeFully(finalName);

        finalName = this.trimEndOfString(finalName);

        return finalName;

    }

    public String trimEndOfString(String s)
    {
        if ( s == null || s.length() == 0 ) {
            return s;
        }

        int i = s.length();

        while ( i > 0 &&  Character.isWhitespace(s.charAt(i - 1)) ) {
            i--;
        }

        if ( i == s.length() ) {
            return s;
        } else {
            return s.substring(0, i);
        }
    }

    public void setMuteAll(boolean muteAll) {
        this.muteAll = muteAll;
    }

    public boolean isMuteAll() {
        return muteAll;
    }

    public ArrayList<String> getMutedPlayers() {
        return mutedPlayers;
    }

    public void addMutedPlayer(Player p) {
        if(!(mutedPlayers.contains(p.getUniqueId().toString()))) {
            mutedPlayers.add(p.getUniqueId().toString());
        }
    }

    public void removeMutedPlayer(Player p) {
        if(mutedPlayers.contains(p.getUniqueId().toString())) {
            mutedPlayers.remove(p.getUniqueId().toString());
        }
    }

    public boolean isMuted(Player p) {
        return mutedPlayers.contains(p.getUniqueId().toString());
    }

}
