package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.game.GameCore;
import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 14/02/2017.
 */
public class Start implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    GameCore gc = GameCore.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(cu.getConsoleMessage());
            return false;
        }

        Player p = (Player) sender;

        if(!(p.hasPermission("uhc.start"))) {
            cu.noPermission(p);
            return false;
        }

        gc.startUHC(p);

        return true;
    }
}
