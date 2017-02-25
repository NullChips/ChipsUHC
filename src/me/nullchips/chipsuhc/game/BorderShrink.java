package me.nullchips.chipsuhc.game;

/**
 * Created by Tommy on 25/02/2017.
 */
public class BorderShrink {

    public BorderShrink(int borderSize, int shrinkTimeMins) {
        this.borderSize = borderSize;
        this.shrinkTimeMins = shrinkTimeMins;
        this.shrinkTimeSeconds = shrinkTimeMins * 60;
    }

    private final int borderSize;
    private final int shrinkTimeMins;
    private final int shrinkTimeSeconds;

    public int getBorderSize() {
        return borderSize;
    }

    public int getShrinkTimeMins() {
        return shrinkTimeMins;
    }

    public int getShrinkTimeSeconds() {
        return shrinkTimeSeconds;
    }
}
