package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class returnConnection {
    public final static String DRIVER = "com.mysql.jdbc.Driver";
    public final static String PROTOCOL = "jdbc:mysql:";
    public final static String DB = "//localhost:3306/libreria";
    public final static String USER = "root";
    public final static String PASS = "";
    
    public static Connection getConnection() throws ClassNotFoundException{	         
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(PROTOCOL+""+DB, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Errore nella connessione col DB",ex);
        }
    }
}
