package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.events.CountdownStartEvent;
import me.nullchips.chipsuhc.game.GameCore;
import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 14/02/2017.
 */
public class Start implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    GameCore gc = GameCore.getInstance();

    public static boolean startCommand = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(cu.getConsoleMessage());
            return false;
        }

        Player p = (Player) sender;

        if(!(p.hasPermission("uhc.start"))) {
            cu.noPermission(p);
            return false;
        }

        if(!(ChipsUHC.isStartTimerRunning()) || !(ChipsUHC.isGameTimerRunning())) {

            if (this.startCommand == false) {
                cu.message(p, ChatColor.RED + "" + ChatColor.BOLD + "Are you sure you want to start the UHC? Type /start again to start, or type /cancel to cancel.");
                this.startCommand = true;
                return true;
            }

            gc.startUHC(p);

            cu.message(p, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Starting the countdown!");

            return true;
        }

        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The game has already been started!");

        return true;
    }

    public boolean isStartCommand() {
        return startCommand;
    }

    public void setStartCommand(boolean startCommand) {
        this.startCommand = startCommand;
    }

}
