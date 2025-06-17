package com.wi3uplus2.cleanup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    static String url = "jdbc:sqlite:src/main/resources/com/wi3uplus2/cleanup/data/data.db";
    static Connection conn;

    // method untuk menghubungkan ke database SQLite
    public static void connect() {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void InsertData(String query) throws SQLException {
        conn.prepareStatement(query).executeUpdate();
    }

    public static int readDataInt(String query, int column) throws SQLException {
        var rs = conn.prepareStatement(query).executeQuery();
        return rs.getInt(column);
    }

    public static void insertMinigameSessionData(int minigameID, boolean isSuccessful) throws SQLException {
        String query = "INSERT INTO MiniGameSession (session_id, minigame_id, is_successful) " +
                "VALUES ((SELECT MAX(session_id) FROM GameSessions), " + minigameID + ", " + (isSuccessful ? 1 : 0) + ");";
        InsertData(query);
    }

    public static void initSession() {
        String query = "INSERT INTO GameSessions (player_id) VALUES (1);";
        try {
            InsertData(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insertSessionData(int totalScore) throws SQLException {
        String query = "UPDATE GameSessions SET score = " + totalScore + " WHERE session_id = (SELECT MAX(session_id) FROM GameSessions);";
        try {
            InsertData(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // masukkan data player ke tabel Achievement (current_condition_num)
    public static void insertToAchievement(int id, int currentNum) throws SQLException {
        String query = "UPDATE Achievement SET current_condition_num = current_condition_num + " + currentNum + " WHERE achievement_id = " + id + ";";
        try {
            InsertData(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // bandingkan current_condition_num dengan condition yang ditentui di level masing-masing
    public static void checkPlayerAchievements(int id, int current_num, int condition) throws SQLException {
        try {
            String query = "SELECT achievement_id FROM PlayerAchievements WHERE player_id = 1 AND achievement_id = " + id + ";";
            int achievementExists = readDataInt(query, 1);
            if (achievementExists > 0 ) {
                System.out.println("Achievement already exists for player.");
                return;
            }

            query = "SELECT current_condition_num FROM Achievement WHERE achievement_id = " + id + ";";
            int currentConditionNum = readDataInt(query, 1);
            if (currentConditionNum >= condition) {
                query = "INSERT INTO PlayerAchievements (player_id, achievement_id) " +
                        "VALUES (1, " + id + ");";
                InsertData(query);
            }else{
                insertToAchievement(id, current_num);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // TODO: ACHIEVEMENTS
    // TODO: PLAYER ACHIEVEMENTS
    // TODO: MINIGAME DESCRIPTION

    // TODO: BETULIN NAMA TABLE DI DATABASE
}
