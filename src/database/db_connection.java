/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import static database.Database.JDBC_DRIVER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author g3nt0
 */
public class db_connection {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String URL_DB      = "jdbc:mysql://localhost/instagram_store";
    static final String username_db = "gika";
    static final String pass_db     = "sayasuka001";
    
    static Connection conn;
    static Statement stmt;
    static ResultSet hasil;
    
    public static void koneksi(){
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
}
