package Interface;

import java.sql.ResultSet;

public interface UtenteInterface {
    public String controlData (String email) throws ClassNotFoundException;
    public boolean insertData (String nome, String cognome, String email, String telefono, String via, String civico, String citta, String provincia, String cap, int negoziante, String password) throws ClassNotFoundException;
    public boolean checkLogin (String email, String password) throws ClassNotFoundException;
    public boolean checkLoginAdmin (String email, String password) throws ClassNotFoundException;
    
}
