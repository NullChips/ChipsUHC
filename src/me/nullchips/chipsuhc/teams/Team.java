package me.nullchips.chipsuhc.teams;

import me.nullchips.chipsuhc.utils.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Tommy on 13/02/2017.
 */
public class Team {

    private TeamUtils tu = TeamUtils.getInstance();

    private ChatColor color;
    private String teamId;
    private ArrayList<String> members;

    public Team(String id, ArrayList<String> members) {
        this.teamId = "UHC" + id;
        this.members = members;
        this.color = tu.getNewTeamColour();

    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public void registerPlayers(ArrayList<String> playerNames) {

        for(String s: playerNames) {
            Player target = Bukkit.getServer().getPlayer(s);

            if(target != null) {
                target.setDisplayName(this.color + target.getName() + ChatColor.WHITE);
                target.setPlayerListName(this.color + target.getName() + ChatColor.WHITE);
            }

        }
    }

    public void addMember(Player target) {

        String s = target.getUniqueId().toString();

        this.getMembers().add(s);

    }

    //TODO List teams command.

}
