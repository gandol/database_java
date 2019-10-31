/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author g3nt0
 */
public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String URL_DB      = "jdbc:mysql://localhost/chickenland_app";
    static final String username_db = "gika";
    static final String pass_db     = "sayasuka001";
    
    static Connection conn;
    static Statement stmt;
    static ResultSet hasil;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            koneksi();
            tampildata("transaksi");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void koneksi(){
        try {
            Class.forName(JDBC_DRIVER);
            conn    = DriverManager.getConnection(URL_DB,username_db,pass_db);
            stmt    = conn.createStatement();
            System.out.println("koneksi berhasil");
        } catch (Exception e) {
            System.out.println("koneksi gagal");
            System.exit(0);
        }
    }
    static void tampildata(String tabelName) {
        try {
            String sql = "SELECT * FROM "+tabelName;
            hasil      = stmt.executeQuery(sql);
            while(hasil.next()){
                System.out.println(hasil.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
    
}
