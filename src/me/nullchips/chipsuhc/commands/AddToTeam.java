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

/**
 * Created by Tommy on 19/02/2017.
 */
public class AddToTeam implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    TeamUtils tu = TeamUtils.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("addtoteam")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if (!(p.hasPermission("uhc.modifyteams"))) {
                cu.noPermission(p);
                return true;
            }

            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Usage: /addtoteam <teamID> <playername>");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);

            if(target == null) {
                p.sendMessage(ChatColor.RED + "Could not find player " + ChatColor.RED + ChatColor.BOLD + args[0]);
                return true;
            }

            Team targetTeam = null;

            for(Team t : tu.getAllTeams()) {
                if (t.getTeamId().equalsIgnoreCase(args[1])) {
                    targetTeam = t;
                }
            }

            if(targetTeam == null) {
                cu.message(p, ChatColor.RED + "That team could not be found.");
                return true;
            }

            if(targetTeam.getMembers().contains(target.getUniqueId().toString())) {
                p.sendMessage(ChatColor.RED + "That team already contains that player.");
            }

            targetTeam.addMember(target);

            target.setDisplayName(targetTeam.getColor() + target.getName() + ChatColor.WHITE);
            target.setPlayerListName(targetTeam.getColor() + target.getName() + ChatColor.WHITE);

            cu.message(p, ChatColor.AQUA + target.getName() + " has been added to team " + ChatColor.AQUA + targetTeam.getTeamId());

        }

        return true;
    }

}
