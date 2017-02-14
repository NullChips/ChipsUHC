package me.nullchips.chipsuhc.game;

import org.bukkit.entity.Player;

/**
 * Created by Tommy on 14/02/2017.
 */
public class GameCore {

    private GameCore() { }

    private static GameCore instance;

    public static GameCore getInstance() {
        if(instance == null) {
            instance = new GameCore();
        }
        return instance;
    }


    //MAIN STARTING METHOD. WILL CONTAIN ALL AUTOMATIC PROCESSES.
    public void startUHC(Player commandSender) {

    }

    public void startCountdown() {

    }

}
