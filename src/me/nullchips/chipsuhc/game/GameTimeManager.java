package me.nullchips.chipsuhc.game;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.GameState;
import me.nullchips.chipsuhc.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    private int minsTillFinalHeal;
    private int secondsTillFinalHeal;

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
                p.setExp(0F);
                pu.clearInventory(p);
                if(gc.getStarterFoodAmount() > 0) {
                    p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, gc.getStarterFoodAmount()));
                }
            }
        }
        pu.setAllFrozen(false);
        pu.setCanUnfreezeAll(true);

        cu.broadcast(ChatColor.GOLD + "Time until final heal: " + getMinsTillFinalHeal());

        setSecondsTillFinalHeal(getMinsTillFinalHeal() * 60);
        startGameTimer();
    }

    private void startGameTimer() {
        gameTimerTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ChipsUHC.getInstance(), new Runnable() {
            @Override
            public void run() {

                //SCOREBOARD UPDATE
                if(secondsInGame %5 == 0) {
                    //TODO Update scoreboard for each player.
                }

                //FINAL HEAL CODE
                if(getSecondsTillFinalHeal() >= 0) {
                    if(getSecondsTillFinalHeal() == 60 || getSecondsTillFinalHeal() == 30 || getSecondsTillFinalHeal() == 20 || getSecondsTillFinalHeal() == 15 || getSecondsTillFinalHeal() <= 10) {
                        if(getSecondsTillFinalHeal() > 0) {
                            cu.broadcast(ChatColor.GOLD + "" + getSecondsTillFinalHeal() + " seconds until final heal!");
                        } else if (getSecondsTillFinalHeal() == 0) {
                            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                                pu.heal(p);
                                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 10);
                            }
                            cu.broadcast(ChatColor.GOLD + "Final heal has been given.");
                        }
                    }
                    setSecondsTillFinalHeal(getSecondsTillFinalHeal() - 1);
                }

                secondsInGame++;
            }
        }, 20L, 20L);
    }

    public int getMinsTillFinalHeal() {
        return minsTillFinalHeal;
    }

    public void setMinsTillFinalHeal(int minsTillFinalHeal) {
        this.minsTillFinalHeal = minsTillFinalHeal;
    }

    public int getSecondsTillFinalHeal() {
        return secondsTillFinalHeal;
    }

    public void setSecondsTillFinalHeal(int secondsTillFinalHeal) {
        this.secondsTillFinalHeal = secondsTillFinalHeal;
    }
}
