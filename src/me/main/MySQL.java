package me.main;
import net.md_5.bungee.BungeeCord;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {


    public static Connection con;

    public static void connect(){

        try {
            File file = new File("plugins/PlayInfinity", "config.yml");
            String host = "localhost";
            String database = "PlayInfinity";
            String username = "root";
            String pass = "";
            con = DriverManager.getConnection("jdbc:mysql://"+host+":3306"+"/"+database, username, pass);
            BungeeCord.getInstance().getConsole().sendMessage("§f[§eMySQL§f] §aVerbunden");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
            BungeeCord.getInstance().getConsole().sendMessage("§f[§eMySQL§f] §cFehler beim Verbinden");
        }
    }

    public static void close(){
        if(!isConnected()){
            try {
                con.close();
                BungeeCord.getInstance().getConsole().sendMessage("§f[§eMySQL§f] §cVerbindung geschlossen");
            } catch (SQLException e) {
                e.printStackTrace();
                BungeeCord.getInstance().getConsole().sendMessage("§f[§eMySQL§f] §cFehler beim schließen der Verbindung!");

            }

        }
    }

    public static boolean isConnected(){
        return con != null;
    }

    public static void createTable(){


        if(isConnected()){
            try {

                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS bansystem (Spielername VARCHAR(100), Team VARCHAR(100), " +
                        "Grund VARCHAR(100), Dauer VARCHAR(100), Banned VARCHAR(100), Unban MEDIUMTEXT)");

                BungeeCord.getInstance().getConsole().sendMessage("§f[§eMySQL§f] §6Tabellen erstellt, falls noch nicht vorhanden");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void update(String qry){
        if(isConnected()){
            try {
                con.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet getResult(String qry){
        if(isConnected()){
            try {
                return con.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
}
