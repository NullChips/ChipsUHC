package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.teams.Team;
import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.GameState;
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
public class RemoveFromTeam implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    TeamUtils tu = TeamUtils.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(cmd.getName().equalsIgnoreCase("removefromteam")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return true;
            }

            Player p = (Player) sender;

            if(!(p.hasPermission("uhc.modifyteams"))) {
                cu.noPermission(p);
                return true;
            }

            if(!GameState.isGameState(GameState.LOBBY)) {
                cu.message(p, ChatColor.RED + "You cannot alter teams when the game is in progress.");
                return true;
            }

            if(!GameState.isGameState(GameState.LOBBY)) {
                cu.message(p, ChatColor.RED + "You cannot alter teams when the game is in progress.");
                return true;
            }

            if(args.length != 1) {
                p.sendMessage(ChatColor.RED + "Usage: /removefromteam <playername>");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);

            if(target == null) {
                p.sendMessage(ChatColor.RED + "Could not find player " + ChatColor.RED + ChatColor.BOLD + args[0]);
                return true;
            }

            if(tu.getTeam(p) == null) {
                p.sendMessage(ChatColor.RED + p.getName() + " is not in a team.");
                return true;
            }

            Team targetTeam = tu.getTeam(p);

            targetTeam.getMembers().remove(p.getUniqueId().toString());

            target.setDisplayName(ChatColor.WHITE + target.getName());
            target.setPlayerListName(ChatColor.WHITE + target.getName());

            cu.message(p, ChatColor.AQUA + p.getName() + ChatColor.GREEN + " has been removed from the team " + ChatColor.AQUA + targetTeam.getTeamId());

            if(targetTeam.getMembers().size() == 0) {
                tu.getAllTeams().remove(targetTeam);
                tu.reloadUsedChatColours();
                p.sendMessage(ChatColor.GREEN + "Team " + ChatColor.AQUA + targetTeam.getTeamId() + ChatColor.GREEN + " contains 0 players, so has been removed.");
            }

        }

        return true;
    }

}
