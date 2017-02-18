package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.features.Feature;
import me.nullchips.chipsuhc.features.FeatureManager;
import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 18/02/2017.
 */
public class FeatureCommand implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    FeatureManager fm = FeatureManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(cmd.getName().equalsIgnoreCase("features")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if(!(p.hasPermission("uhc.getfeatures"))) {
                cu.noPermission(p);
                return true;
            }

            for(Feature f : fm.getAllFeatures()) {
                if(f.isEnabled()) {
                    p.sendMessage(ChatColor.GREEN + f.getDisplayName() + ": Enabled.");
                    return true;
                } else {
                    p.sendMessage(ChatColor.RED + f.getDisplayName() + ": Disabled.");
                    return true;
                }
            }
        }
        return true;
    }


}
