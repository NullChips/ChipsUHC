package me.nullchips.chipsuhc;

import me.nullchips.chipsuhc.commands.*;
import me.nullchips.chipsuhc.features.FeatureManager;
import me.nullchips.chipsuhc.game.GameCore;
import me.nullchips.chipsuhc.game.StartTimeManager;
import me.nullchips.chipsuhc.listeners.player.PlayerChat;
import me.nullchips.chipsuhc.listeners.player.PlayerJoin;
import me.nullchips.chipsuhc.listeners.player.PlayerLeave;
import me.nullchips.chipsuhc.listeners.PlayerRegainHealth;
import me.nullchips.chipsuhc.listeners.player.PlayerMove;
import me.nullchips.chipsuhc.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Tommy on 13/02/2017.
 */
public class ChipsUHC extends JavaPlugin {

    private static Plugin instance;
    private static int seconds;
    private static boolean startTimerRunning;
    private static boolean gameTimerRunning;

    private TeamUtils tu;
    private SettingsManager settingsManager;
    private GameCore gc;
    private StartTimeManager stm;
    private FeatureManager fm;
    private PlayerUtils pu;

    public static SpreadPlayers sp;

    @Override
    public void onEnable() {
        instance = this;
        seconds = 0;
        startTimerRunning = false;
        gameTimerRunning = false;

        GameState.setGameState(GameState.LOBBY);

        tu = TeamUtils.getInstance();
        settingsManager = SettingsManager.getInstance();
        gc = GameCore.getInstance();
        stm = StartTimeManager.getInstance();
        fm = FeatureManager.getInstance();
        sp = SpreadPlayers.getInstance();
        pu = PlayerUtils.getInstance();

        settingsManager.setup(this);

        settingsManager.loadConfigSettings();

        pu.setAllFrozen(false);
        pu.setCanUnfreezeAll(true);
        pu.setFreezeAllSender(null);

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
        tu.addPossibleTeamColour(ChatColor.YELLOW);

        //REGISTER COMMANDS

        getCommand("test").setExecutor(new Test());
        getCommand("start").setExecutor(new Start());
        getCommand("cancel").setExecutor(new Cancel());
        getCommand("gmc").setExecutor(new GMC());
        getCommand("gms").setExecutor(new GMS());
        getCommand("clear").setExecutor(new Clear());
        getCommand("heal").setExecutor(new Heal());
        getCommand("feed").setExecutor(new Feed());
        getCommand("helpop").setExecutor(new HelpOp());
        getCommand("giveall").setExecutor(new GiveAll());
        getCommand("health").setExecutor(new Health());
        getCommand("muteall").setExecutor(new MuteAll());
        getCommand("mute").setExecutor(new Mute());
        getCommand("unmute").setExecutor(new Unmute());
        getCommand("features").setExecutor(new FeatureCommand());
        getCommand("createteam").setExecutor(new CreateTeam());
        getCommand("removeteam").setExecutor(new RemoveTeam());
        getCommand("removefromteam").setExecutor(new RemoveFromTeam());
        getCommand("addtoteam").setExecutor(new AddToTeam());
        getCommand("createuhcworld").setExecutor(new CreateUHCWorld());
        getCommand("freeze").setExecutor(new Freeze());
        getCommand("unfreeze").setExecutor(new Unfreeze());
        getCommand("freezeall").setExecutor(new FreezeAll());
        getCommand("unfreezeall").setExecutor(new UnfreezeAll());


        //REGISTER LISTENERS

        registerEvents(this, new PlayerRegainHealth());
        registerEvents(this, new PlayerJoin());
        registerEvents(this, new PlayerLeave());
        registerEvents(this, new PlayerChat());
        registerEvents(this, new PlayerMove());

        Bukkit.getServer().getScheduler().runTaskTimer(this, stm, 20L, 20L);

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


    public static int getSeconds() {
        return seconds;
    }

    public static void setSeconds(int seconds) {
        ChipsUHC.seconds = seconds;
    }

    public static boolean isStartTimerRunning() {
        return startTimerRunning;
    }

    public static void setStartTimerRunning(boolean startTimerRunning) {
        ChipsUHC.startTimerRunning = startTimerRunning;
    }

    public static boolean isGameTimerRunning() {
        return gameTimerRunning;
    }

    public static void setGameTimerRunning(boolean gameTimerRunning) {
        ChipsUHC.gameTimerRunning = gameTimerRunning;
    }


    public static Player getPlayerFromUUID(String s) {

        Player foundPlayer = null;

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(p.getUniqueId().toString().equals(s)) {
                foundPlayer = p;
                break;
            }
        }

        return foundPlayer;
    }

    public static Player getPlayerFromUUID(UUID u) {

        Player foundPlayer = null;

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(p.getUniqueId().equals(u)) {
                foundPlayer = p;
                break;
            }
        }

        return foundPlayer;

    }

}
