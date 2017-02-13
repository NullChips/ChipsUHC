package me.nullchips.chipsuhc.utils;

/**
 * Created by Tommy on 13/02/2017.
 */
public class ChatUtils {

    private ChatUtils() { }

    private static ChatUtils instance;

    public static ChatUtils getInstance() {
        if(instance == null) {
            instance = new ChatUtils();
        }
        return instance;
    }



}
