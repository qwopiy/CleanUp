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

    public void insertData(String query) throws SQLException {
        var conn = DriverManager.getConnection(url);
        conn.prepareStatement(query);
    }


}
