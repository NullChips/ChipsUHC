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
 * Created by Tommy on 14/02/2017.
 */
public class Clear implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    PlayerUtils pu = PlayerUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(cu.getConsoleMessage());
            return true;
        }

        Player p = (Player) sender;

        if(!(p.hasPermission("uhc.clear"))) {
            cu.noPermission(p);
            return true;
        }

        if(args.length == 0) {
            pu.clearInventory(p);
            cu.message(p, "Your inventory has been cleared.");
            return true;
        }

        if(args[0].equalsIgnoreCase("*")
                ) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if(!(player.getName() == p.getName())) {
                    pu.clearInventory(player);
                    cu.message(player, "Your inventory has been cleared by " + ChatColor.GOLD + p.getName() + ChatColor.WHITE + ".");
                }
            }
            cu.message(p, "Cleared the inventory of all players.");
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);

        if(target == null) {
            cu.message(p, ChatColor.RED + "Could not find player: " + ChatColor.WHITE + ChatColor.BOLD + args[0]);
            return true;
        }

        pu.clearInventory(target);
        cu.message(p, "You have cleared " + ChatColor.GOLD + target.getName() + "s" + ChatColor.WHITE + "inventory.");
        cu.message(target, "Your inventory has been cleared by " + ChatColor.GOLD + p.getName() + ChatColor.WHITE + ".");

        return true;
    }

}
