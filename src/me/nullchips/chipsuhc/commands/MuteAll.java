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
public class MuteAll implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(cmd.getName().equalsIgnoreCase("muteall")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if (!(p.hasPermission("uhc.mute"))) {
                cu.noPermission(p);
                return true;
            }

            if (cu.isMuteAll()) {
                cu.setMuteAll(false);
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    cu.message(player, ChatColor.AQUA + "" + ChatColor.BOLD + "Global mute is now disabled!");
                    player.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
                }
                return true;
            }

            cu.setMuteAll(true);
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                cu.message(player, ChatColor.AQUA + "" + ChatColor.BOLD + "Global mute is now enabled!");
                player.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
            }
        }
        return true;
    }
}
