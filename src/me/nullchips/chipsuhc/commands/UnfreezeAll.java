package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 23/02/2017.
 */
public class UnfreezeAll implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    PlayerUtils pu = PlayerUtils.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("unfreezeall")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if(!p.hasPermission("uhc.freeze")) {
                cu.noPermission(p);
                return true;
            }

            if(!pu.canUnfreezeAll()) {
                p.sendMessage(ChatColor.RED + "Cannot alter frozen state of all players. All players are currently frozen by the game.");
                return true;
            }

            if(!pu.isAllFrozen()) {
                p.sendMessage(ChatColor.RED + "All players are not frozen.");
                return true;
            }

            pu.setAllFrozen(false);
            for(Player target : Bukkit.getServer().getOnlinePlayers()) {
                if(p.getName() != target.getName()) {
                    pu.unfreeze(target);
                }
            }

            pu.setFreezeAllSender(null);
            cu.broadcast(ChatColor.RED + "" + ChatColor.BOLD + "All players have been unfrozen by " + ChatColor.AQUA + p.getName());

        }

        return true;
    }

}
