package me.nullchips.chipsuhc.utils;

import me.nullchips.chipsuhc.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 13/02/2017.
 */
public class TeamUtils {

    private static TeamUtils instance;

    private int teamId = 1;

    private ArrayList<ChatColor> possibleTeamColours;
    private ArrayList<ChatColor> usedTeamColours;

    private List<Team> allTeams = new ArrayList<Team>();

    private ScoreboardManager scoreboardManager = Bukkit.getServer().getScoreboardManager();
    private Scoreboard board = scoreboardManager.getNewScoreboard();

    private TeamUtils() { }

    public static TeamUtils getInstance() {
        if(instance == null) {
            instance = new TeamUtils();
        }
        return instance;
    }

    public boolean hasTeam(Player p) {
        for(Team t : this.getAllTeams()) {
            if (t.getMembers().contains(p.getUniqueId().toString())) {
                return true;
            }
        }
        return false;
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void addTeam(Team team) {
        this.allTeams.add(team);
    }

    public void removeTeam(Team team) {
        this.allTeams.remove(team);
    }

    public void clearTeams() {
        this.allTeams.clear();
    }

    public ArrayList<ChatColor> getPossibleTeamColours() {
        return possibleTeamColours;
    }

    public void setPossibleTeamColours(ArrayList<ChatColor> possibleTeamColours) {
        this.possibleTeamColours = possibleTeamColours;
    }

    public void addPossibleTeamColour(ChatColor c) {
        this.possibleTeamColours.add(c);
    }

    public ArrayList<ChatColor> getUsedTeamColours() {
        return usedTeamColours;
    }

    public void setUsedTeamColours(ArrayList<ChatColor> usedTeamColours) {
        this.usedTeamColours = usedTeamColours;
    }

    public ChatColor getNewTeamColour() {
        ChatColor cc = null;
        for(ChatColor co : this.getPossibleTeamColours()) {
            if (!this.getUsedTeamColours().contains(co)) {
                cc = co;
                break;
            }
        }

        reloadUsedChatColours();
        return cc;
    }

    public Scoreboard getBoard() {
        return board;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Team getTeam(Player p) {
        String uuid = p.getUniqueId().toString();

        for(Team team : this.allTeams) {
            if (team.getMembers().contains(uuid)) {
                return team;
            }
        }

        return null;

    }

    public void reloadUsedChatColours() {

        this.usedTeamColours.clear();

        for(Team t : this.allTeams) {
            this.usedTeamColours.add(t.getColor());
        }
    }

}
