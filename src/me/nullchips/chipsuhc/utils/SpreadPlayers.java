package me.nullchips.chipsuhc.utils;

import me.nullchips.chipsuhc.ChipsUHC;
import me.nullchips.chipsuhc.game.GameCore;
import me.nullchips.chipsuhc.game.GameTimeManager;
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
    GameTimeManager gtm = GameTimeManager.getInstance();

    private int chunkLoadDelay;
    private int maxTries;
    private int minDistance;
    private int taskId;
    private ArrayList<Team> teamsToSpread = new ArrayList<>();
    private ArrayList<String> soloPlayersToSpread = new ArrayList<>();
    private ArrayList<Location> locationsToSpreadTo = new ArrayList<>();

    //NEW SPREAD VARIABLES

    private int teamsLeftToSpread;
    private int playersLeftToSpread;
    private int teamIndex;
    private int locationIndex;
    private int playerIndex;
    private boolean messageDisplayed;


    @Override
    public void run() {

        long l = (long) this.chunkLoadDelay;
        l = l/3;
        long chunkLoadDelayLong = l*2;

        if(this.teamsLeftToSpread != 0) {

            this.messageDisplayed = false;

            Location tpLocation = this.locationsToSpreadTo.get(this.locationIndex);
            Team t = this.teamsToSpread.get(this.teamIndex);

            for(String s : t.getMembers()) {
                Player p = ChipsUHC.getPlayerFromUUID(s);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ChipsUHC.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        if(p.isOnline() && p != null) {
                            p.teleport(tpLocation);
                            if (!messageDisplayed) {
                                cu.broadcast(ChatColor.GREEN + "Scattered team " + ChatColor.AQUA + t.getTeamId() + ChatColor.GREEN + ".");
                                messageDisplayed = true;
                                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                    p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
                                }
                            }
                        } else {
                            cu.broadcast(ChatColor.RED + "Player not online in team " + t.getTeamId() + ".");
                            //TODO Better system for handling offline players?
                        }
                    }
                }, chunkLoadDelayLong);
            }

            this.locationIndex++;
            this.teamIndex ++;
            this.teamsLeftToSpread--;

            long finalDelayLong = (long) chunkLoadDelay;

            if(this.teamsLeftToSpread == 0 && playersLeftToSpread == 0) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ChipsUHC.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        cu.broadcast(ChatColor.GOLD + "" + ChatColor.BOLD + "Player spread finished.");
                        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 7);
                        }
                        finishSpread();
                        soloPlayersToSpread.clear();
                        teamsToSpread.clear();

                    }
                },finalDelayLong);
            }

        } else if (this.playersLeftToSpread != 0) {
            Location tpLocation = this.locationsToSpreadTo.get(this.locationIndex);
            Player p = ChipsUHC.getPlayerFromUUID(this.soloPlayersToSpread.get(playerIndex));

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ChipsUHC.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(p.isOnline() && p != null) {
                        p.teleport(tpLocation);
                        cu.broadcast(ChatColor.GREEN + "Scattered player " + ChatColor.AQUA + p.getName() + ChatColor.GREEN + ".");
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 7);
                        }
                    } else {
                        cu.broadcast(ChatColor.RED + "Player not online.");
                        //TODO Better system for handling offline players?
                    }
                }
            }, chunkLoadDelayLong);

            this.locationIndex++;
            this.playerIndex++;
            this.playersLeftToSpread--;

            long finalDelayLong = (long) this.chunkLoadDelay;

            if(this.teamsLeftToSpread == 0 && playersLeftToSpread == 0) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ChipsUHC.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        cu.broadcast(ChatColor.GOLD + "" + ChatColor.BOLD + "Player spread finished.");
                        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 7);
                        }
                        finishSpread();
                        soloPlayersToSpread.clear();
                        teamsToSpread.clear();

                    }
                },finalDelayLong);

            }

        }
    }

    private void finishSpread() {

        Bukkit.getServer().getScheduler().cancelTask(taskId);

        gtm.startGameCountdown();

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

                    int locationY = gameWorld.getHighestBlockYAt(locationX, locationZ) - 1;

                    Location blockLocation = new Location(gameWorld, locationX, locationY, locationZ);

                    Block locationBlock = blockLocation.getBlock();

                    if (locationBlock.getType() == Material.LAVA || locationBlock.getType() == Material.WATER || locationBlock.getType() == Material.CACTUS || locationBlock.getType() == Material.STATIONARY_WATER || locationBlock.getType() == Material.STATIONARY_LAVA || locationBlock.getType() == Material.WATER_LILY) {
                        locationOkay = false;
                    }

                    spreadSender.sendMessage(locationBlock.getType().name());

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

        this.teamsLeftToSpread = this.teamsToSpread.size();
        this.playersLeftToSpread = this.soloPlayersToSpread.size();
        this.teamIndex = 0;
        this.locationIndex = 0;
        this.playerIndex = 0;

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
