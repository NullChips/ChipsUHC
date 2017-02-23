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
public class Unfreeze implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    PlayerUtils pu = PlayerUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("unfreeze")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if(!p.hasPermission("uhc.freeze")) {
                cu.noPermission(p);
                return true;
            }

            if(args.length != 1) {
                p.sendMessage(ChatColor.RED + "Usage: /unfreeze <player>");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);

            if(target == null) {
                cu.message(p, ChatColor.RED + "Could not find player: " + ChatColor.WHITE + ChatColor.BOLD + args[0]);
                return true;
            }

            if(pu.isAllFrozen()) {
                p.sendMessage(ChatColor.RED + "Cannot unfreeze a player whilst all players are frozen.");
                return true;
            }

            if(!pu.isFrozen(target)) {
                p.sendMessage(ChatColor.RED + "Player is not frozen. Use /freeze to freeze a player.");
                return true;
            }

            pu.unfreeze(target);
            pu.getFrozenPlayers().remove(p.getUniqueId().toString());
            cu.message(target, ChatColor.RED + "" + ChatColor.BOLD + "You have been unfrozen by " + ChatColor.AQUA + p.getName() + ChatColor.RED + ".");
            cu.message(p, ChatColor.GREEN + "You have frozen " + target.getName() + ChatColor.GREEN + ".");

        }
        return true;
    }

}
