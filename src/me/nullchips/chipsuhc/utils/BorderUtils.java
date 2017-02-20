package me.nullchips.chipsuhc.utils;

/**
 * Created by Tommy on 20/02/2017.
 */
public class BorderUtils {

    private BorderUtils() { }

    private static BorderUtils instance;

    public static BorderUtils getInstance() {
        if(instance == null) {
            instance = new BorderUtils();
        }
        return instance;
    }

    private int startRadius;

    public int getStartRadius() {
        return startRadius;
    }

    public void setStartRadius(int startRadius) {
        this.startRadius = startRadius;
    }
}
