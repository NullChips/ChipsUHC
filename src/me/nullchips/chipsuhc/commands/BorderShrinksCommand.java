package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.game.BorderShrink;
import me.nullchips.chipsuhc.game.BorderShrinkManager;
import me.nullchips.chipsuhc.utils.BorderUtils;
import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 25/02/2017.
 */
public class BorderShrinksCommand implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    BorderShrinkManager bsm = BorderShrinkManager.getInstance();
    BorderUtils bu = BorderUtils.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("bordershrinks")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p  = (Player) sender;

            if(!p.hasPermission("uhc.bordershrinks")) {
                cu.noPermission(p);
                return true;
            }

            if(bsm.getAllShrinks().size() == 0) {
                int i  = bu.getStartRadius() * 2;
                p.sendMessage(ChatColor.RED + "No border shrinks have been set. The border will remain at " + i + "x" + i + " throughout the game.");
            }

            for(BorderShrink bs : bsm.getAllShrinks()) {
                p.sendMessage(ChatColor.GREEN + "Borders shrink to size " + ChatColor.AQUA + bs.getBorderSize() + "x" + bs.getBorderSize() +
                        ChatColor.GREEN + ", " + ChatColor.GOLD + bs.getShrinkTimeMins() + ChatColor.GREEN + " minutes into the game.");
            }

        }


        return true;
    }

}
