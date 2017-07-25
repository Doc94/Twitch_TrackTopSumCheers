package me.mrdoc.twitchtopcheers.managers;

import me.mrdoc.twitchtopcheers.classes.Cheering;
import me.mrdoc.twitchtopcheers.db.SQLiteSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created on 20-07-2017 for TwitchGOTH_TOPSumCheers.
 *
 * @author Doc
 */
public class DBCheerManagent {

    public static boolean purgeTable() {
        String command = "DELETE FROM cheerings ;";

        PreparedStatement psPurge = SQLiteSystem.getPreparedStatement(command);

        int result = 0;
        try {
            result = psPurge.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (result != 0);
    }

    public static boolean addCheer(String username, int mount) {

        String commandInsert = "INSERT OR IGNORE INTO cheerings VALUES (?, ?);";

        String commandUpdate = "UPDATE cheerings SET cheermount = cheermount + ? WHERE username LIKE ?;";

        PreparedStatement psInsert = SQLiteSystem.getPreparedStatement(commandInsert);

        try {
            psInsert.setString(1,username);
            psInsert.setInt(2,mount);

            int result = psInsert.executeUpdate();

            if(result == 0) { //Si es cero entonces existe el registro y aplicamos UPDATE
                PreparedStatement psUpdate = SQLiteSystem.getPreparedStatement(commandUpdate);
                psUpdate.setInt(1,mount);
                psUpdate.setString(2,username);
                result = psUpdate.executeUpdate();
            }

            return (result != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Cheering getCheer(String username) {
        String command = "SELECT * FROM cheerings WHERE username LIKE ? ;";

        PreparedStatement psGetCheer = SQLiteSystem.getPreparedStatement(command);

        try {

            psGetCheer.setString(1,username);

            ResultSet rs = psGetCheer.executeQuery();

            if(rs.next()) {
                return new Cheering(rs.getString("username"),rs.getInt("cheermount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static HashMap<Integer,Cheering> getTOPCheers(int cant) {
        HashMap<Integer,Cheering> cheers = new HashMap<Integer,Cheering>();

        String command = "SELECT * FROM cheerings ORDER BY cheermount DESC LIMIT ? ;";

        PreparedStatement psGetCheer = SQLiteSystem.getPreparedStatement(command);

        try {

            psGetCheer.setInt(1,cant);

            ResultSet rs = psGetCheer.executeQuery();

            int pos = 1;

            while(rs.next()) {
                Cheering cheer = new Cheering(rs.getString("username"),rs.getInt("cheermount"));
                cheers.put(pos,cheer);
                pos = pos + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cheers;
    }

}
