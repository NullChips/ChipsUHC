package me.nullchips.chipsuhc.teams;

import me.nullchips.chipsuhc.utils.TeamUtils;
import org.bukkit.ChatColor;

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

    public Team(String id, ArrayList<String> members) {
        this.name = "UHC" + id;
        this.members = members;
        this.color = tu.getNewTeamColour();
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
}
