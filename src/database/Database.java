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
    static final String URL_DB      = "jdbc:mysql://localhost/instagram_store";
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
            hapusData();
//            String email =  JOptionPane.showInputDialog("masukkan email User");
//            String pass   = JOptionPane.showInputDialog("masukkan password Update");
//            if(updateDataUser(email,pass)){
//                System.out.println("Update berhasil");
//            }else{
//                System.out.println("proses update gagal");
//            }
//            delete();
//            if(login()){
//                delete();
//            }else{
//                System.out.println("Login gagal");
//            }
            
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
                String update_dateTime  = "update loginUser SET  last_login=NOW() WHERE email='"+username+"'";
                PreparedStatement prepare   = conn.prepareStatement(update_dateTime);
                prepare.execute();
                status=true;
            }
            return status;
        } catch (Exception e) {
            status=false;
            return status;
        }
        
    }
    
    
    
    static boolean  cekIfDataExists(String tabel,String kolom, String value){
        boolean found = false;
        try {
            String sql = "SELECT * FROM "+tabel+" WHERE "+kolom+"='"+value+"'";
            hasil       = stmt.executeQuery(sql);
            while(hasil.next()){
                found   = true;
            }
        } catch (Exception e) {
            found   = false;
        }
        return found;
    }
    
    static boolean updateDataUser(String email,String passUpdate)
    {
        boolean sukses  = false;
        if(cekIfDataExists("loginUser", "email", email)){
            try {
                String query = "UPDATE loginUser SET password='"+passUpdate+"' WHERE email='"+email+"'";
                PreparedStatement prepare = conn.prepareStatement(query);
                prepare.execute();
                sukses=true;
            } catch (Exception e) {
                sukses=false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "User not Found","Alert",JOptionPane.ERROR_MESSAGE);
        }
        
        return sukses;
    }
    
    static void hapusData(){
        String data = JOptionPane.showInputDialog("data yang mau di hapus");
        if(cekIfDataExists("datashop","kode",data)){
            try {
                String query_delete = "DELETE FROM datashop WHERE kode='"+data+"'";
                PreparedStatement prepare = conn.prepareStatement(query_delete);
                prepare.execute();
                System.out.println("Hapus Data Berhasil");
            } catch (Exception e) {
                System.err.println("Terjadi kesalahn");
                System.exit(0);
            }
            
        }else{
            System.out.println("maaf data tidak dapat ditemukan");
        }
    }
    
}
