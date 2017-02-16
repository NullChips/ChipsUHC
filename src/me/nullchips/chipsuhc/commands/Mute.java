package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 16/02/2017.
 */
public class Mute implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(cmd.getName().equalsIgnoreCase("mute")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if (!(p.hasPermission("uhc.mute"))) {
                cu.noPermission(p);
                return true;
            }

            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Usage: /mute <player>");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);

            if (target == null) {
                cu.message(p, ChatColor.RED + "Could not find player: " + ChatColor.WHITE + ChatColor.BOLD + args[0]);
                return true;
            }

            if (cu.isMuted(target)) {
                p.sendMessage(ChatColor.RED + "That player is already muted. Use /unmute to unmute a player.");
                return true;
            }

            cu.addMutedPlayer(p);

            cu.message(p, ChatColor.GREEN + target.getName() + " has been muted.");
            target.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
            cu.message(target, ChatColor.RED + "" + ChatColor.BOLD + "You have been muted!");
        }

        return true;
    }
}
