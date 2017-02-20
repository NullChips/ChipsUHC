package me.nullchips.chipsuhc.utils;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.game.GameCore;
import me.nullchips.chipsuhc.teams.Team;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tommy on 15/02/2017.
 */

public class SpreadPlayers implements Runnable {

    private SpreadPlayers() { }
    private static SpreadPlayers instance;

    public static SpreadPlayers getInstance() {
        if(instance == null) {
            instance = new SpreadPlayers();
        }
        return instance;
    }

    TeamUtils tu = TeamUtils.getInstance();
    GameCore gc = GameCore.getInstance();
    BorderUtils bu = BorderUtils.getInstance();
    ChatUtils cu = ChatUtils.getInstance();

    private int chunkLoadDelay;
    private int maxTries;
    private int minDistance;
    private int taskId;
    private ArrayList<Team> teamsToSpread = new ArrayList<>();
    private ArrayList<String> soloPlayersToSpread = new ArrayList<>();
    private ArrayList<Location> locationsToSpreadTo = new ArrayList<>();

    private Location lastLocation;
    private Team lastTeam;
    private Player lastPlayer;
    private int spreadCount = 0;
    private int locationCount;
    private boolean teamsAreSpread;

    @Override
    public void run() {

        if(lastTeam != null) {

            cu.broadcast(ChatColor.GREEN + "Spreading team " + ChatColor.AQUA + this.lastTeam.getTeamId());
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
            }

            for(String s : lastTeam.getMembers()) {
                Player player = ChipsUHC.getPlayerFromUUID(s);
                if(player != null) {
                    player.teleport(lastLocation);
                }
            }

            lastTeam = null;
            lastLocation = null;

        } else if (lastPlayer != null) {

            cu.broadcast(ChatColor.GREEN + "Spreading player " + ChatColor.AQUA + lastPlayer.getName());

            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
            }

            lastPlayer.teleport(lastLocation);
            lastPlayer = null;
            lastLocation = null;

        }

        if(teamsToSpread.size() != 0) {
           this.lastTeam = this.teamsToSpread.get(0);
           this.teamsToSpread.remove(0);
        } else {
            if(teamsAreSpread = false) {
                cu.broadcast(ChatColor.BLUE + "Teams are fully spread, now spreading solo players.");
            }
            this.lastPlayer = ChipsUHC.getPlayerFromUUID(this.soloPlayersToSpread.get(0));
            this.soloPlayersToSpread.remove(0);
        }

        this.lastLocation = this.locationsToSpreadTo.get(0);

        spreadCount++;

        if(spreadCount == locationCount) {
            if(lastTeam != null) {
                cu.broadcast(ChatColor.GREEN + "Spreading team " + ChatColor.AQUA + this.lastTeam.getTeamId());

                for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
                }

                for(String s : lastTeam.getMembers()) {
                    Player player = ChipsUHC.getPlayerFromUUID(s);
                    if(player != null) {
                        player.teleport(lastLocation);
                    }
                }

                lastTeam = null;
                lastLocation = null;

            } else if (lastPlayer != null) {

                cu.broadcast(ChatColor.GREEN + "Spreading player " + ChatColor.AQUA + lastPlayer.getName());

                for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
                }

                lastPlayer.teleport(lastLocation);
                lastPlayer = null;
                lastLocation = null;

            }

            finishSpread();
        }
    }

    private void finishSpread() {

        Bukkit.getServer().getScheduler().cancelTask(taskId);

        cu.broadcast(ChatColor.GREEN + "" + ChatColor.BOLD + "Playerspread finished!");

        gc.startMatch();

    }

    public boolean registerSpreadLoactions(Player spreadSender) {

        int i = registerPlayersToSpread();

        boolean breakLoop = false;

        for(int loop = 0; loop < i; loop++) {
            if(!breakLoop) {
                for (int loop2 = 0; loop2 < this.maxTries; loop2++) {
                    boolean locationOkay = true;

                    int locationX = getRandomNumber(0 - bu.getStartRadius(),  bu.getStartRadius());
                    int locationZ = getRandomNumber(0 - bu.getStartRadius(), bu.getStartRadius());

                    String worldName = gc.getGameWorldName();

                    World gameWorld = Bukkit.getServer().getWorld(worldName);

                    int locationY = gameWorld.getHighestBlockYAt(locationX, locationZ);

                    Location blockLocation = new Location(gameWorld, locationX, locationY, locationZ);

                    Block locationBlock = blockLocation.getBlock();

                    if (locationBlock.getType() == Material.LAVA || locationBlock.getType() == Material.WATER || locationBlock.getType() == Material.CACTUS) {
                        locationOkay = false;
                    }

                    int teleportY = blockLocation.getBlockY() + 1;

                    Location newLocation = new Location(gameWorld, locationX, teleportY, locationZ);

                    for (Location l : this.locationsToSpreadTo) {
                        if (getDistanceBetween(l, newLocation) < this.minDistance) {
                            locationOkay = false;
                        }
                    }

                    if (locationOkay) {
                        this.locationsToSpreadTo.add(newLocation);
                        break;
                    }

                    if (loop2 == this.maxTries - 1) {
                        breakLoop = true;
                    }

                }
            }

            if(breakLoop) {
                cu.message(spreadSender, ChatColor.RED + "" + ChatColor.BOLD + "Max tries reached, with no appropriate locations found. Try a larger border radius, smaller distance between players, or a map with less ocean.");
                return false;
            }
        }
        return true;
    }

    public void spreadPlayers(Player p) {
        final long chunkLoadDelayLong = (long) chunkLoadDelay;
        this.locationCount = 0;
        if(this.teamsToSpread.size() != 0) {
            teamsAreSpread = false;
        }
        taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ChipsUHC.getInstance(), this, chunkLoadDelayLong, chunkLoadDelayLong);
    }

    public int getChunkLoadDelay() {
        return chunkLoadDelay;
    }

    public void setChunkLoadDelay(int chunkLoadDelay) {
        this.chunkLoadDelay = chunkLoadDelay;
    }

    public int getMaxTries() {
        return maxTries;
    }

    public void setMaxTries(int maxTries) {
        this.maxTries = maxTries;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }


    public ArrayList<Location> getLocationsToSpreadTo() {
        return locationsToSpreadTo;
    }

    public void setLocationsToSpreadTo(ArrayList<Location> locationsToSpreadTo) {
        this.locationsToSpreadTo = locationsToSpreadTo;
    }

    public int registerPlayersToSpread() {

        int i = 0;

        for(Team t : tu.getAllTeams()) {
            if(t.getMembers().size() != 0) {
                this.teamsToSpread.add(t);
                i++;
            }
        }

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!(tu.hasTeam(p)) && gc.isPlaying(p)) {
                this.soloPlayersToSpread.add(p.getUniqueId().toString());
                i++;
            }
        }

        return i;

    }

    public int getRandomNumber(int lowerBound, int upperBound) {
        Random random = new Random();
        int i = random.nextInt(upperBound + 1 -lowerBound) + lowerBound;
        return i;
    }

    public int getDistanceBetween(Location location1, Location location2) {
        return (int) location1.distance(location2);
    }
}
