package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 16/02/2017.
 */
public class Health implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("health")) {

            if(!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if(args.length == 0) {
                p.sendMessage(ChatColor.RED + "Usage: /h <player>");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);

            if(target == null) {
                cu.message(p, ChatColor.RED + "Could not find player: " + ChatColor.WHITE + ChatColor.BOLD + args[0]);
                return true;
            }

            cu.message(p, ChatColor.GREEN + target.getName() + ChatColor.WHITE + " is on " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " health.");

        }

        return true;

    }

}
