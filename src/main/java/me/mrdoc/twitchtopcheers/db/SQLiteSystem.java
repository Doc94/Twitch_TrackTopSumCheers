package me.mrdoc.twitchtopcheers.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created on 20-07-2017 for TwitchGOTH_TOPSumCheers.
 *
 * @author Doc
 */
public class SQLiteSystem {

    private static Connection dbConnection;


    private static void connectSystem() {
        try {
            Class.forName("org.sqlite.JDBC");
            dbConnection = DriverManager.getConnection("jdbc:sqlite:TOPCheersSum.db");
            System.out.println("Connection to SQLite has been established.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void disconnectSystem() {
        if(dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        try {
            if(dbConnection == null || dbConnection.isClosed()) {
                connectSystem();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

    public static PreparedStatement getPreparedStatement(String command) {
        try {
            PreparedStatement st = getConnection().prepareStatement(command);
            st.setQueryTimeout(30);
            return st;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void run() {
        connectSystem();
        String command = "create table if not exists cheerings (username VARCHAR(50) PRIMARY KEY, cheermount INTEGER)";
        try {
            getPreparedStatement(command).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
