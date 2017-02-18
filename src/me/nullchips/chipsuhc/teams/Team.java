package me.nullchips.chipsuhc.teams;

import me.nullchips.chipsuhc.utils.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Tommy on 13/02/2017.
 */
public class Team {

    private TeamUtils tu = TeamUtils.getInstance();

    private ChatColor color;
    private String name;
    private ArrayList<String> members;
    private org.bukkit.scoreboard.Team team;

    public Team(String id, ArrayList<String> members) {
        this.name = "UHC" + id;
        this.members = members;
        this.color = tu.getNewTeamColour();

        tu.addUsedTeamColour(this.color);

        this.team = tu.getBoard().registerNewTeam(id);
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public org.bukkit.scoreboard.Team getTeam() {
        return team;
    }

    public void registerPlayers(ArrayList<String> playerNames) {

        for(String s: playerNames) {
            Player target = Bukkit.getServer().getPlayer(s);

            if(target != null) {
                team.addPlayer(target);
                target.setDisplayName(this.color + target.getName() + ChatColor.WHITE);
                target.setPlayerListName(this.color + target.getName() + ChatColor.WHITE);
                //TODO Ensure that there are colours left before creating a team!
            }

        }
    }
}
