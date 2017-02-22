package me.nullchips.chipsuhc.game;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Tommy on 14/02/2017.
 */
public class GameCore {

    private GameCore() { }

    private static GameCore instance;

    public static GameCore getInstance() {
        if(instance == null) {
            instance = new GameCore();
        }
        return instance;
    }

    ChatUtils cu = ChatUtils.getInstance();
    PlayerUtils pu = PlayerUtils.getInstance();
    TeamUtils tu = TeamUtils.getInstance();
    //SpreadPlayers sp = SpreadPlayers.getInstance();

    private String gameWorldName;
    private ArrayList<String> notPlaying = new ArrayList<>();

    //MAIN STARTING METHOD. WILL CONTAIN ALL AUTOMATIC PROCESSES.
    public void startUHC(Player p) {
        cu.message(p, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Starting the countdown!");
        ChipsUHC.setStartTimerRunning(true);
        GameState.setGameState(GameState.STARTING);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ChipsUHC.getInstance(), new Runnable() {
            @Override
            public void run() {
                ChipsUHC.sp.spreadPlayers(p);
            }
        }, 250L);

    }

    public void prepareSpread() {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            cu.message(p, ChatColor.GOLD + "" + ChatColor.BOLD + "Player spread starting now!");
            p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1, 10);
            pu.freeze(p);
        }

    }

    public boolean canStart(Player player) {

        if(Bukkit.getWorld(this.getGameWorldName()) == null) {
            cu.message(player, ChatColor.RED + "" + ChatColor.BOLD + "The world with the name " + ChatColor.GOLD + ChatColor.BOLD + this.getGameWorldName() + ChatColor.RED + ChatColor.BOLD + " cannot be found! Cannot start game!");
            return false;
        }

        if(tu.getAllTeams().size() == 0) {
            cu.message(player, ChatColor.YELLOW + "" +ChatColor.BOLD + "WARNING: No teams have been created. Playerspread will continue as with players on their own.");
        }



        if(!ChipsUHC.sp.registerSpreadLoactions(player)) {
            return false;
        }

        return true;

    }

    public String getGameWorldName() {
        return gameWorldName;
    }

    public void setGameWorldName(String gameWorldName) {
        this.gameWorldName = gameWorldName;
    }

    public ArrayList<String> getNotPlaying() {
        return notPlaying;
    }

    public void setNotPlaying(ArrayList<String> notPlaying) {
        this.notPlaying = notPlaying;
    }

    public boolean isPlaying(Player p) {
        if(this.notPlaying.contains(p.getUniqueId().toString())) {
            return false;
        } else {
            return true;
        }
    }

    public void startMatch() {
        //TODO Create start match logic for after players have been spread.
    }
}
