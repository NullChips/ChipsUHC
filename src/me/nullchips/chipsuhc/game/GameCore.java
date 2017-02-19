package me.nullchips.chipsuhc.game;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.events.CountdownStartEvent;
import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.GameState;
import me.nullchips.chipsuhc.utils.PlayerUtils;
import me.nullchips.chipsuhc.utils.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
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
    PlayerUtils pu = PlayerUtils.getInstance();
    TeamUtils tu = TeamUtils.getInstance();

    private String gameWorldName;

    //MAIN STARTING METHOD. WILL CONTAIN ALL AUTOMATIC PROCESSES.
    public void startUHC(Player p) {

        if(this.canStart(p)) {

            Bukkit.getServer().getPluginManager().callEvent(new CountdownStartEvent(p));
            ChipsUHC.setStartTimerRunning(true);
            GameState.setGameState(GameState.STARTING);

        }
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

        return true;

    }

    public String getGameWorldName() {
        return gameWorldName;
    }

    public void setGameWorldName(String gameWorldName) {
        this.gameWorldName = gameWorldName;
    }
}
