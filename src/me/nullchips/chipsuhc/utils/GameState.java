package me.nullchips.chipsuhc.utils;

/**
 * Created by Tommy on 13/02/2017.
 */
public enum GameState {
    LOBBY(true), STARTING(false), SPREAD(false), IN_GAME(false), CLEANUP(false);

    public boolean canNewPlayerJoin;

    private static GameState gameState;

    GameState(boolean canNewPlayerJoin) {
        this.canNewPlayerJoin = canNewPlayerJoin;
    }

    public static void setGameState(GameState state) {
        GameState.gameState = state;
    }

    public static boolean isGameState(GameState state) {
        return GameState.gameState == state;
    }

    public static GameState getGameState() {
        return gameState;
    }

}
