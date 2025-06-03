package com.wi3uplus2.cleanup;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    static String url = "jdbc:sqlite:src/main/resources/com/wi3uplus2/cleanup/data/data.db";

    // method untuk menghubungkan ke database SQLite
    public static void connect() {

        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertData(String query) throws SQLException {
        var conn = DriverManager.getConnection(url);
        conn.prepareStatement(query).executeUpdate();
    }

    public static void insertMinigameSessionData(int minigameID, boolean isSuccessful) throws SQLException {
        String query = "INSERT INTO MiniGameSession (session_id, minigame_id, is_successful) VALUES (1, " + minigameID + ", " + (isSuccessful ? 1 : 0) + ");";
        insertData(query);
    }

    public static void initSession() {
        String query = "INSERT INTO GameSessions (player_id) VALUES (1);";
        try {
            insertData(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insertSessionData(int totalScore) throws SQLException {
        String query = "INSERT INTO GameSessions (score) VALUES (" + totalScore + ");";
        try {
            insertData(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
