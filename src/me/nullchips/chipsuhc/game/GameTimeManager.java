package me.nullchips.chipsuhc.game;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.GameState;
import me.nullchips.chipsuhc.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 22/02/2017.
 */
public class GameTimeManager {

    private GameTimeManager() { }

    private static GameTimeManager instance;

    public static GameTimeManager getInstance() {
        if(instance == null) {
            instance = new GameTimeManager();
        }
        return instance;
    }

    ChatUtils cu = ChatUtils.getInstance();
    PlayerUtils pu = PlayerUtils.getInstance();
    GameCore gc = GameCore.getInstance();

    private int secondsUntilStart;
    private int secondsInGame;

    private int countdownTaskID;
    private int gameTimerTaskID;

    public void startGameCountdown() {
        this.secondsUntilStart = 30;
        GameState.setGameState(GameState.STARTING);
        countdownTaskID  = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ChipsUHC.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(secondsUntilStart %5 == 0 || secondsUntilStart < 11) {
                    cu.broadcast(ChatColor.BLUE + "" + ChatColor.BOLD + "Match starting in "
                            + ChatColor.GREEN + "" + ChatColor.BOLD + secondsUntilStart + ChatColor.BLUE + "" + ChatColor.BOLD + " seconds.");
                    for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 10);
                    }
                }

                if(secondsUntilStart == 0) {
                    cu.broadcast(ChatColor.GOLD + "" + ChatColor.BOLD + "Game starting now!");
                    for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1, 10);
                    }
                    endGameCountdown();
                    GameState.setGameState(GameState.IN_GAME);
                }

                secondsUntilStart--;
            }
        },20L, 20L);
    }

    public void endGameCountdown() {
        Bukkit.getServer().getScheduler().cancelTask(countdownTaskID);
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(!gc.getNotPlaying().contains(p.getUniqueId().toString())) {
                pu.unfreeze(p);
                pu.heal(p);
                pu.feed(p);
                p.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

}
