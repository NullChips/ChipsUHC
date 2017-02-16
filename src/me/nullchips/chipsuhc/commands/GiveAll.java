package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tommy on 15/02/2017.
 */
public class GiveAll implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(cu.getConsoleMessage());
            return true;
        }

        Player p = (Player) sender;

        if(!(p.hasPermission("uhc.giveall"))) {
            cu.noPermission(p);
            return true;
        }

        if(args.length == 0 || args.length > 2) {
            p.sendMessage(ChatColor.RED + "Usage: /giveall <item> <amount>");
            return true;
        }

        if (args.length == 1) {
            String s = args[0];

            if(s.equalsIgnoreCase("0")) {
                p.sendMessage(ChatColor.RED + "Item does not exist.");
                return true;
            }

            Material m = Material.matchMaterial(s);

            if (m == null) {
                p.sendMessage(ChatColor.RED + "Item does not exist.");
                return true;
            }

            ItemStack is = new ItemStack(m,1);

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                player.getInventory().addItem(is);
                player.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
                cu.message(player, ChatColor.GOLD + p.getName() + ChatColor.WHITE + " has given all players: " + ChatColor.GREEN + cu.convertItemName(m) + ChatColor.WHITE + ".");
            }

            cu.message(p, "Given all players " + ChatColor.GREEN + cu.convertItemName(m) + ChatColor.WHITE + ".");

            return true;

        }

        String amount = args[1];

        if(!(cu.isInteger(amount))) {
            p.sendMessage("Amount to give is not a number.");
            return true;
        }

        int intAmount = Integer.parseInt(amount);
        String s = args [0];

        if(intAmount == 0) {
            p.sendMessage(ChatColor.RED + "Cannot give a player 0 of an item.");
            return true;
        }

        if(s.equalsIgnoreCase("0")) {
            p.sendMessage(ChatColor.RED + "Item does not exist.");
            return true;
        }

        Material m = Material.matchMaterial(s);

        if(m == null) {
            p.sendMessage(ChatColor.RED + "Item does not exist.");
            return true;
        }

        ItemStack is = new ItemStack(m, intAmount);

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.getInventory().addItem(is);
            player.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
            cu.message(player, ChatColor.GOLD + p.getName() + ChatColor.WHITE + " has given all players: " + ChatColor.GREEN + amount + " " + cu.convertItemName(m) + ChatColor.WHITE + ".");
        }

        cu.message(p, "Given all players " + ChatColor.GREEN + amount + " " + cu.convertItemName(m) + ChatColor.WHITE + ".");
        return true;

    }
}
