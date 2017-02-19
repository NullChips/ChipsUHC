package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.teams.Team;
import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Tommy on 19/02/2017.
 */
public class RemoveTeam implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    TeamUtils tu = TeamUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String comamndLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("removeteam")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if (!(p.hasPermission("uhc.modifyteams"))) {
                cu.noPermission(p);
                return true;
            }

            if(args.length != 1) {
                p.sendMessage(ChatColor.RED + "Usage: /removeteam <teamID>");
                return true;
            }

            Team target = null;

            String s = args[0];

            for(Team t : tu.getAllTeams()) {
                if (t.getTeamId().equalsIgnoreCase(s)) {
                    target = t;
                }
            }

            if(target == null) {
                cu.message(p, ChatColor.RED + "That team could not be found.");
                return true;
            }

            for(String string : target.getMembers()) {

                Player foundPlayer = null;

                for(Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                    if(onlinePlayer.getUniqueId().toString().equals(string)) {
                            foundPlayer = p;
                    }
                }

                if(foundPlayer != null) {
                    foundPlayer.setDisplayName(ChatColor.WHITE + foundPlayer.getName());
                    foundPlayer.setPlayerListName(ChatColor.WHITE + foundPlayer.getName());
                }
            }

            tu.reloadUsedChatColours();
            tu.removeTeam(target);

            return true;
        }

        return true;

    }
}
