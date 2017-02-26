package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Created by Tommy on 26/02/2017.
 */
public class ReloadConfigCommand implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    SettingsManager sm = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("reloadconfig")) {

            if(!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if(!p.hasPermission("uhc.relaodconfig")) {
                cu.noPermission(p);
                return true;
            }

            sm.loadConfigSettings();
            cu.message(p, "The config has been reloaded.");

        }
        return true;
    }
}
