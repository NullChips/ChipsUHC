package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tommy on 14/02/2017.
 */
public class Test implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("test")) {
            cu.message((Player) sender, "Test message");
        }
        return true;
    }
}
