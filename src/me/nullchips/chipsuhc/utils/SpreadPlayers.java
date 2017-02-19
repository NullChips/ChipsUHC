package me.nullchips.chipsuhc.utils;

import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.swing.plaf.synth.SynthRootPaneUI;
import java.util.ArrayList;

/**
 * Created by Tommy on 15/02/2017.
 */

public class SpreadPlayers implements Runnable {

    private SpreadPlayers() { }
    private static SpreadPlayers instance;

    public static SpreadPlayers getInstace() {
        if(instance == null) {
            instance = new SpreadPlayers();
        }
        return instance;
    }

    private int chunkLoadDelay;
    private int maxTires;

    @Override
    public void run() {

    }

    public void spreadPlayers(World world, Player spreadSender, ArrayList<String> playersToSpread, int chunkLoadDelay,int maxTries) {

    }

    public int getChunkLoadDelay() {
        return chunkLoadDelay;
    }

    public void setChunkLoadDelay(int chunkLoadDelay) {
        this.chunkLoadDelay = chunkLoadDelay;
    }

    public int getMaxTires() {
        return maxTires;
    }

    public void setMaxTires(int maxTires) {
        this.maxTires = maxTires;
    }
}
