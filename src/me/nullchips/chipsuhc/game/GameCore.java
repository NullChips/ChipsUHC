package me.nullchips.chipsuhc.game;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

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

    //MAIN STARTING METHOD. WILL CONTAIN ALL AUTOMATIC PROCESSES.
    public void startUHC(Player commandSender) {
        ChipsUHC.setStartTimerRunning(true);
    }

    public void prepareSpread() {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            cu.message(p, ChatColor.GOLD + "" + ChatColor.BOLD + "Player spread starting now!");
            p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1, 10);
        }
    }
}
