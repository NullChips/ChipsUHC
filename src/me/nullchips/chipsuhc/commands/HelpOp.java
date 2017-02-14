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
 * Created by Tommy on 14/02/2017.
 */
public class HelpOp implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(cmd.getName().equalsIgnoreCase("helpop")) {

            if(!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if(args.length == 0) {
                p.sendMessage(ChatColor.RED + "Usage: /helpop <message>");
                return true;
            }

            String msg = "";

            for (String s : args) {
                msg = (msg + s + " ");
            }

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if(p.hasPermission("uhc.helpop")) {
                    cu.message(player, ChatColor.GREEN + "Helpop from " + ChatColor.GOLD + p.getName() + ": " + ChatColor.WHITE + msg);
                    player.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
                }
            }

            cu.message(p, "Helpop sent.");

        }

        return true;
    }
}
