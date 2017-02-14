package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 14/02/2017.
 */
public class GMS implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(cu.getConsoleMessage());
            return true;
        }

        Player p = (Player) sender;

        if(!(p.hasPermission("uhc.changegamemode"))) {
            cu.noPermission(p);
            return true;
        }

        if(p.getGameMode().equals(GameMode.SURVIVAL)) {
            cu.message(p, ChatColor.RED + "You are already in survival mode.");
            return true;
        }

        p.setGameMode(GameMode.SURVIVAL);
        cu.message(p, "Your gamemode has been set to survival.");

        return true;
    }

}
