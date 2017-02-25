package me.nullchips.chipsuhc.game;

import java.util.ArrayList;

/**
 * Created by Tommy on 25/02/2017.
 */
public class BorderShrinkManager {

    private BorderShrinkManager() { }

    private static BorderShrinkManager instance;

    public static BorderShrinkManager getInstance() {
        if(instance == null) {
            instance = new BorderShrinkManager();
        }
        return instance;
    }

    private ArrayList<BorderShrink> allShrinks = new ArrayList<>();
    private BorderShrink activeShrink = null;

    public ArrayList<BorderShrink> getAllShrinks() {
        return allShrinks;
    }

    public void addBorderShrink(BorderShrink bs) {
        this.allShrinks.add(bs);
    }

    public void removeBorderShrink(BorderShrink bs) {
        this.allShrinks.remove(bs);
    }

    public BorderShrink getActiveShrink() {
        return activeShrink;
    }

    public void setActiveShrink(BorderShrink activeShrink) {
        this.activeShrink = activeShrink;
    }
}
