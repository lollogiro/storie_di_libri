package DAO;

import Model.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteDAO implements Interface.UtenteInterface{
    
    @Override
    public String controlData (String email) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstm = connessione.prepareStatement("SELECT * FROM utenti WHERE Email = ?");
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return "emailEX";
            }
            else{
                return "ok";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "err";
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public boolean insertData (String nome, String cognome, String email, String telefono, String via, String civico, String citta, String provincia, String cap, int negoziante, String password) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        LocalDate data_iscrizione = LocalDate.now();
        
        try {
            PreparedStatement pstmAdd = connessione.prepareStatement("INSERT INTO utenti (Email, Nome, Cognome, Telefono, Via, Civico, Città, Provincia, CAP, Data_Iscrizione, Negoziante, Password) VALUES (?,?,?,?,?,?,?,?,?,?,?,md5(?))");
            pstmAdd.setString(1, email);
            pstmAdd.setString(2, nome);
            pstmAdd.setString(3, cognome);
            pstmAdd.setString(4, telefono);
            pstmAdd.setString(5, via);
            pstmAdd.setString(6, civico);
            pstmAdd.setString(7, citta);
            pstmAdd.setString(8, provincia);
            pstmAdd.setString(9, cap);
            pstmAdd.setString(10, data_iscrizione.toString());
            pstmAdd.setInt(11, negoziante);
            pstmAdd.setString(12, password);
            System.out.println(pstmAdd.toString());
            pstmAdd.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public boolean checkLogin (String email, String password) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstm = connessione.prepareStatement("SELECT * FROM utenti WHERE Email = ? and Password = md5(?) and Negoziante = 0");
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public boolean checkLoginAdmin (String email, String password) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstm = connessione.prepareStatement("SELECT * FROM utenti WHERE Email = ? and Password = md5(?) and Negoziante = 1");
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UtenteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    
    
    
}
