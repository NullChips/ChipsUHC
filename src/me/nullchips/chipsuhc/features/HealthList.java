package me.nullchips.chipsuhc.features;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.utils.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;

/**
 * Created by Tommy on 17/02/2017.
 */
public class HealthList extends Feature implements Runnable {

    private SettingsManager sm = SettingsManager.getInstance();
    private FeatureManager fm = FeatureManager.getInstance();

    private static ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private static Scoreboard board = scoreboardManager.getMainScoreboard();
    private static Objective objPlayerList = null;
    private static int taskId;

    public HealthList() {
        super("Tab List Health", "tab-list-health", true);
    }

    @Override
    public void run() {
        this.updatePlayers();
    }

    @Override
    public void onEnable() {
        initializeScoreboard();
        taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ChipsUHC.getInstance(), this, 20L, 20L);
    }

    @Override
    public void onDisable() {
        if (board != null) {
            board.clearSlot(DisplaySlot.PLAYER_LIST);
            Bukkit.getServer().getScheduler().cancelTask(taskId);
        }

    }

    private void initializeScoreboard() {

        try {
            board.registerNewObjective("UHCHealth", "dummy");
        } catch (IllegalArgumentException localIllegalArgumentException1) { }

        objPlayerList = board.getObjective("UHCHealth");
        objPlayerList.setDisplaySlot(DisplaySlot.PLAYER_LIST);

    }

    public void updatePlayers() {

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            Score score = objPlayerList.getScore(p);
            double d = p.getHealth() * 5;
            score.setScore((int) d);
        }
    }
}
