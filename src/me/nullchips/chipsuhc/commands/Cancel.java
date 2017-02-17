package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 17/02/2017.
 */
public class Cancel implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(cu.getConsoleMessage());
            return true;
        }

        Player p = (Player) sender;

        if(!(p.hasPermission("uhc.start"))) {
            cu.noPermission(p);
            return true;
        }

        if(!(ChipsUHC.isGameTimerRunning()) && !(ChipsUHC.isStartTimerRunning())) {

            if (!(Start.startCommand)) {
                p.sendMessage(ChatColor.RED + "Start command has not been executed, so therefore there is nothing to cancel.");
                return true;
            }

            Start.startCommand = false;
            p.sendMessage(ChatColor.RED + "The start command has been cancelled.");

            return true;
        }

        p.sendMessage(ChatColor.RED + "The game has already been started!");

        return true;
    }
}
