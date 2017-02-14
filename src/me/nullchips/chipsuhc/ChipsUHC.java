package me.nullchips.chipsuhc;

import me.nullchips.chipsuhc.commands.Test;
import me.nullchips.chipsuhc.listeners.PlayerJoin;
import me.nullchips.chipsuhc.listeners.PlayerLeave;
import me.nullchips.chipsuhc.listeners.PlayerRegainHealth;
import me.nullchips.chipsuhc.utils.SettingsManager;
import me.nullchips.chipsuhc.utils.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by Tommy on 13/02/2017.
 */
public class ChipsUHC extends JavaPlugin {

    private static Plugin instance;
    private static int ticks;

    private TeamUtils tu;
    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        instance = this;
        ticks = 0;

        tu = TeamUtils.getInstance();
        settingsManager = SettingsManager.getInstance();

        settingsManager.setup(this);

        settingsManager.loadConfigSettings();

        //ADD ALL POSSIBLE TEAM COLOURS

        tu.setPossibleTeamColours(new ArrayList<ChatColor>());
        tu.setUsedTeamColours(new ArrayList<ChatColor>());

        tu.addPossibleTeamColour(ChatColor.AQUA);
        tu.addPossibleTeamColour(ChatColor.BLACK);
        tu.addPossibleTeamColour(ChatColor.BLUE);
        tu.addPossibleTeamColour(ChatColor.BOLD);
        tu.addPossibleTeamColour(ChatColor.DARK_AQUA);
        tu.addPossibleTeamColour(ChatColor.DARK_BLUE);
        tu.addPossibleTeamColour(ChatColor.DARK_GRAY);
        tu.addPossibleTeamColour(ChatColor.DARK_GREEN);
        tu.addPossibleTeamColour(ChatColor.DARK_PURPLE);
        tu.addPossibleTeamColour(ChatColor.DARK_RED);
        tu.addPossibleTeamColour(ChatColor.GOLD);
        tu.addPossibleTeamColour(ChatColor.GRAY);
        tu.addPossibleTeamColour(ChatColor.ITALIC);
        tu.addPossibleTeamColour(ChatColor.LIGHT_PURPLE);
        tu.addPossibleTeamColour(ChatColor.RED);
        tu.addPossibleTeamColour(ChatColor.STRIKETHROUGH);
        tu.addPossibleTeamColour(ChatColor.UNDERLINE);
        tu.addPossibleTeamColour(ChatColor.WHITE);
        tu.addPossibleTeamColour(ChatColor.YELLOW);

        //REGISTER COMMANDS

        getCommand("test").setExecutor(new Test());

        //REGISTER LISTENERS

        registerEvents(this, new PlayerRegainHealth());
        registerEvents(this, new PlayerJoin());
        registerEvents(this, new PlayerLeave());

        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Test");

    }

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }


    @Override
    public void onDisable() {
        instance = null;
    }

    public static Plugin getInstance() {
        return instance;
    }

}
