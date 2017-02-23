package me.nullchips.chipsuhc.game;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 17/02/2017.
 */
public class StartTimeManager implements Runnable {

    private StartTimeManager() { }

    private static StartTimeManager instance = new StartTimeManager();

    public static StartTimeManager getInstance() {
        return instance;
    }

    ChatUtils cu = ChatUtils.getInstance();

    private int secondsUntilSpread = 10;


    @Override
    public void run() {
        if(ChipsUHC.isStartTimerRunning()) {

            ChipsUHC.setSeconds(ChipsUHC.getSeconds() + 1);

            if(ChipsUHC.getSeconds() <= 10) {

                for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                    cu.message(p, ChatColor.BLUE + "" + ChatColor.BOLD + "Spreading players in "
                            + ChatColor.GREEN + "" + ChatColor.BOLD + secondsUntilSpread + ChatColor.BLUE + "" + ChatColor.BOLD + " seconds.");
                    secondsUntilSpread --;
                    p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 10);
                }

            }

            if(ChipsUHC.getSeconds() == 11) {
                GameState.setGameState(GameState.SPREAD);
                GameCore.getInstance().prepareSpread();
            }

        }
    }
}
