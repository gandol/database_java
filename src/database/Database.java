/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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
            String email    = JOptionPane.showInputDialog("masukkan email");
            String passUser = JOptionPane.showInputDialog("masukkan pass yang mau di update");
            if(UpdateUser(email, passUser)){
                System.out.println("Update Berhasil");
            }
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
    static boolean login(){
        boolean status;
        try {
            status=false;
            String username = JOptionPane.showInputDialog("masukkan email");
            String passwd   = JOptionPane.showInputDialog("masukkan pasword");
            String sql = "SELECT * from loginUser where email='"+username+"' AND password='"+passwd+"'";
            hasil      = stmt.executeQuery(sql);
            while(hasil.next()){
                status=true;
            }
            return status;
        } catch (Exception e) {
            status=false;
            return status;
        }
        
    }
    
    static boolean UpdateUser(String email,String passUpdate)
    {
        boolean sukses  = false;
        if(cekIfExist("loginUser", "email", email)){
            try {
                String sql  = "UPDATE loginUser set password ='"+passUpdate+"' WHERE email='"+email+"'";
                PreparedStatement prepare = conn.prepareStatement(sql);
                prepare.execute();
                sukses      = true;
            } catch (Exception e) {
                sukses      = false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "User Tidak Ada","Alert",JOptionPane.ERROR_MESSAGE);
        }
        return sukses;
    }
    
    static boolean cekIfExist(String tabel,String kolom,String value){
        boolean found   = false;
        try {
            String query    = "SELECT * FROM "+tabel+" WHERE "+kolom+"='"+value+"'";
            hasil           = stmt.executeQuery(query);
            while(hasil.next()){
                found=true;
            }
        } catch (Exception e) {
            found   = false;
        }
        return found;
    }
}            
