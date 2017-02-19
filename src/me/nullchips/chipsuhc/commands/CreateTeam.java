package me.nullchips.chipsuhc.commands;

import com.sun.org.apache.regexp.internal.RE;
import me.nullchips.chipsuhc.teams.Team;
import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Tommy on 18/02/2017.
 */
public class CreateTeam implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    TeamUtils tu = TeamUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String comamndLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("createteam")) {

            if(!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if(!(p.hasPermission("uhc.modifyteams"))) {
                cu.noPermission(p);
                return true;
            }

            if(args.length == 0) {
                p.sendMessage(ChatColor.RED + "Usage: /createteam <player1> <player2> <player3>...");
                return true;
            }

            if(tu.getUsedTeamColours().size() == tu.getPossibleTeamColours().size()) {
                cu.message(p, ChatColor.RED + "No more teams can be created!");
            }

            ArrayList<String> playersToAdd= new ArrayList<String>();

            for(String s : args) {

                Player target = Bukkit.getServer().getPlayer(s);

                if(target == null) {
                    p.sendMessage(ChatColor.RED + "Could not find player " + ChatColor.RED + ChatColor.BOLD + s);
                    return true;
                }

                if (playersToAdd.contains(s)) {
                    p.sendMessage(ChatColor.RED + "Cannot add player " + ChatColor.RED + ChatColor.BOLD + s + ChatColor.RESET + ChatColor.RED + " to the team twice!");
                    return true;
                }

                if(tu.hasTeam(target)) {
                    p.sendMessage(ChatColor.RED + p.getName() + " already has a team!");
                    return true;
                }

                playersToAdd.add(s);

            }

            ArrayList<String> playersToAddUUID= new ArrayList<String>();

            for(String s : playersToAdd) {

                Player target = Bukkit.getServer().getPlayer(s);

                if(target != null) {
                    playersToAddUUID.add(target.getUniqueId().toString());
                }

            }

            int teamId = tu.getAllTeams().size() + 1;
            String teamIdString = Integer.toString(teamId);

            Team team = new Team(teamIdString, playersToAddUUID);
            team.registerPlayers(playersToAdd);

            tu.addTeam(team);

            cu.message(p, ChatColor.GREEN + "Team " + ChatColor.AQUA + team.getName() + ChatColor.GREEN + " has been created successfully.");

        }
        return true;
    }
}
