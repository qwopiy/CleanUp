package com.wi3uplus2.cleanup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    public static int GetHighScore() {
        String query = "SELECT high_score FROM player;";
        try {
            return readDataInt(query, 1);
        } catch (SQLException e) {
            System.err.println("Error saat mengambil high score: " + e.getMessage());
            return 0;
        }
    }

    public static List<PlayerAchievmentElement> getAllPlayerAchievement() {
        List<PlayerAchievmentElement> playerAchievement = new ArrayList<>();
        String query = "SELECT a.achievement_id, a.name, pa.unlocked_at " +
                "FROM achievement a " +
                "LEFT JOIN player_achievement pa ON a.achievement_id = pa.achievement_id " +
                "ORDER BY a.achievement_id ASC";
        try{;
            var rs = conn.prepareStatement(query).executeQuery();
            while (rs.next()) {
                int achievementId = rs.getInt("achievement_id");
                String name = rs.getString("name");
                String unlockedAt = rs.getString("unlocked_at") == null ? "null" : rs.getString("unlocked_at");;

                playerAchievement.add(new PlayerAchievmentElement(achievementId, name, unlockedAt));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua produk: " + e.getMessage());
        }
        return playerAchievement;
    }

    public static void insertMinigameSessionData(int minigameID, boolean isSuccessful) throws SQLException {
        String query = "INSERT INTO MiniGame_Session (session_id, minigame_id, is_successful) " +
                "VALUES ((SELECT MAX(session_id) FROM Game_Session), " + minigameID + ", " + (isSuccessful ? 1 : 0) + ");";
        InsertData(query);
    }

    public static void initSession() {
        String query = "INSERT INTO Game_Session (player_id) VALUES (1);";
        try {
            InsertData(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insertSessionData(int totalScore) throws SQLException {
        String query = "UPDATE Game_Session SET score = " + totalScore + " WHERE session_id = (SELECT MAX(session_id) FROM GameSession);";
        try {
            InsertData(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // masukkan data player ke tabel Achievement (current_condition_num)
    public static void insertToAchievement(int id, int currentNum) throws SQLException {
        String query = "UPDATE Achievement SET current_condition = current_condition + " + currentNum + " WHERE achievement_id = " + id + ";";
        try {
            InsertData(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // bandingkan current_condition_num dengan condition yang ditentui di level masing-masing
    public static void checkPlayerAchievements(int id, int current_num, int condition) throws SQLException {
        try {
            String query = "SELECT achievement_id FROM Player_Achievement WHERE player_id = 1 AND achievement_id = " + id + ";";
            int achievementExists = readDataInt(query, 1);
            if (achievementExists > 0 ) {
                System.out.println("Achievement already exists for player.");
                return;
            }

            query = "SELECT current_condition FROM Achievement WHERE achievement_id = " + id + ";";
            int currentConditionNum = readDataInt(query, 1);
            if (currentConditionNum >= condition) {
                query = "INSERT INTO Player_Achievement (player_id, achievement_id) " +
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
