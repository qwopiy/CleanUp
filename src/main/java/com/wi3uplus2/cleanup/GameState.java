package com.wi3uplus2.cleanup;

import java.sql.SQLException;

public class GameState {
    public static int currentLives = 3;
    public static int currentScore = 0;
    public static int nextGame = 1;

    public static void endGame() throws SQLException {
        DatabaseHandler.insertSessionData(currentScore);
    }
}
