package DAO;

import Model.Carrello;
import Model.Libro;
import Model.Prestito;
import static java.lang.Integer.parseInt;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibroDAO implements Interface.LibroInterface{
    
    private Libro returnLibro(ResultSet rs) throws SQLException{
        Libro libro = new Libro();
        libro.setID_Libro(rs.getString("ID_Libro"));
        libro.setTitolo(rs.getString("Titolo"));
        libro.setAutore(rs.getString("Autore"));
        libro.setGenere(rs.getString("Genere"));
        libro.setPrezzo(rs.getString("Prezzo"));
        libro.setQuantita(rs.getString("Quantità"));
        return libro;
    }
    
    @Override
    public Set<Libro> showLibri() throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try {
            Statement stm = connessione.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM libri");
            Set libreria = new HashSet();
            while(rs.next()){
                if(rs.getInt("Quantità") <= 0){
                    
                }
                else{
                    Libro libro = returnLibro(rs);
                    libreria.add(libro);
                }
                
            }
            return libreria;
        }catch (SQLException ex) {
            return null;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public String addLibro(Libro libro) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstmFind = connessione.prepareStatement("SELECT * FROM libri WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
            pstmFind.setString(1, libro.getTitolo());
            pstmFind.setString(2, libro.getAutore());
            pstmFind.setString(3, libro.getGenere());
            pstmFind.setString(4, libro.getPrezzo());
            ResultSet rsFind = pstmFind.executeQuery();
            if (rsFind.next()){
                PreparedStatement pstmUpdate = connessione.prepareStatement("UPDATE libri SET Quantità = ? WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
                pstmUpdate.setInt(1, parseInt(libro.getQuantita())+rsFind.getInt("Quantità"));
                pstmUpdate.setString(2, libro.getTitolo());
                pstmUpdate.setString(3, libro.getAutore());
                pstmUpdate.setString(4, libro.getGenere());
                pstmUpdate.setString(5, libro.getPrezzo());
                pstmUpdate.executeUpdate();
                return "alreadyEX";
            }
            else{
                PreparedStatement pstmAdd = connessione.prepareStatement("INSERT INTO libri (Titolo, Autore, Genere, Prezzo, Quantità) VALUES (?,?,?,?,?)");
                pstmAdd.setString(1, libro.getTitolo());
                pstmAdd.setString(2, libro.getAutore());
                pstmAdd.setString(3, libro.getGenere());
                pstmAdd.setString(4, libro.getPrezzo());
                pstmAdd.setString(5, libro.getQuantita());
                pstmAdd.execute();
                return "add";
            }
        }catch (SQLException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "err";
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }   

    @Override
    public String removeLibro(String titolo, String autore, String genere, String prezzo) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstmFind = connessione.prepareStatement("SELECT * FROM libri WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
            pstmFind.setString(1, titolo);
            pstmFind.setString(2, autore);
            pstmFind.setString(3, genere);
            pstmFind.setString(4, prezzo);
            ResultSet rsFind = pstmFind.executeQuery();
            if (rsFind.next()){
                PreparedStatement pstmDelete = connessione.prepareStatement("DELETE FROM libri WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
                pstmDelete.setString(1, titolo);
                pstmDelete.setString(2, autore);
                pstmDelete.setString(3, genere);
                pstmDelete.setString(4, prezzo);
                pstmDelete.execute();
                return "ok";
            }
            else{
                return "noEX";
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "err";
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public String updateLibro(Libro libro) throws ClassNotFoundException {
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstmFind = connessione.prepareStatement("SELECT * FROM libri WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
            pstmFind.setString(1, libro.getTitolo());
            pstmFind.setString(2, libro.getAutore());
            pstmFind.setString(3, libro.getGenere());
            pstmFind.setString(4, libro.getPrezzo());
            ResultSet rsFind = pstmFind.executeQuery();
            if (rsFind.next()){
                if(parseInt(rsFind.getString("Quantità")) == 0){
                    return "qNO";
                }
                else{
                    Libro libroFinded = returnLibro(rsFind);
                    int quantitaFinale = parseInt(libroFinded.getQuantita()) - parseInt(libro.getQuantita());
                    if (quantitaFinale>0){
                        PreparedStatement pstmUpdate = connessione.prepareStatement("UPDATE libri SET Quantità = ? WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
                        pstmUpdate.setString(1, String.valueOf(quantitaFinale));
                        pstmUpdate.setString(2, libro.getTitolo());
                        pstmUpdate.setString(3, libro.getAutore());
                        pstmUpdate.setString(4, libro.getGenere());
                        pstmUpdate.setString(5, libro.getPrezzo());
                        pstmUpdate.executeUpdate();
                        return "qOK";
                    }
                    else{
                        PreparedStatement pstmFind1 = connessione.prepareStatement("SELECT * FROM libriprestiti WHERE ID_Libro = ?");
                        pstmFind1.setString(1, rsFind.getString("ID_Libro"));
                        ResultSet rsFind1 = pstmFind1.executeQuery();
                        if(rsFind1.next()){
                            PreparedStatement pstmUpdate = connessione.prepareStatement("UPDATE libri SET Quantità = ? WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
                            pstmUpdate.setString(1, String.valueOf(quantitaFinale));
                            pstmUpdate.setString(2, libro.getTitolo());
                            pstmUpdate.setString(3, libro.getAutore());
                            pstmUpdate.setString(4, libro.getGenere());
                            pstmUpdate.setString(5, libro.getPrezzo());
                            pstmUpdate.executeUpdate();
                            return "qOK";
                        }
                        else{
                            PreparedStatement pstmDelete = connessione.prepareStatement("DELETE FROM libri WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
                            pstmDelete.setString(1, libro.getTitolo());
                            pstmDelete.setString(2, libro.getAutore());
                            pstmDelete.setString(3, libro.getGenere());
                            pstmDelete.setString(4, libro.getPrezzo());
                            pstmDelete.execute();
                            return "qFIN";
                        }

                    }
                }
                
            }
            else{
                return "noEX";
            }
        }catch (SQLException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "err";
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public boolean checkPrestito (Libro libro, String email) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try{
            PreparedStatement pstmFind = connessione.prepareStatement("SELECT * FROM libri WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
            pstmFind.setString(1, libro.getTitolo());
            pstmFind.setString(2, libro.getAutore());
            pstmFind.setString(3, libro.getGenere());
            pstmFind.setString(4, libro.getPrezzo());
            ResultSet rsFind = pstmFind.executeQuery();
            
            if (rsFind.next()){
                PreparedStatement pstmFind2 = connessione.prepareStatement("SELECT * FROM libriprestiti WHERE ID_Libro = ? and Email = ?");
                pstmFind2.setString(1, rsFind.getString("ID_Libro"));
                pstmFind2.setString(2, email);
                ResultSet rsFind2 = pstmFind2.executeQuery();
                if(rsFind2.next()){
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public boolean addBookToCart(String email, String id_libro) throws ClassNotFoundException {
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstmFind = connessione.prepareStatement("SELECT * FROM CarrelloLibri WHERE Email = ? and ID_Libro = ?");
            pstmFind.setString(1, email);
            pstmFind.setString(2, id_libro);
            ResultSet rsFind = pstmFind.executeQuery();
            PreparedStatement pstmFind1 = connessione.prepareStatement("SELECT * FROM Libri WHERE ID_Libro = ?");
            pstmFind1.setString(1, id_libro);
            ResultSet rsFind1 = pstmFind1.executeQuery();
            
            int qLibri = 0;
            if(rsFind1.next()){
                qLibri = rsFind1.getInt("Quantità");
            }
                
            if(rsFind.next()){
                int quantità = rsFind.getInt("Quantità");
                quantità = quantità + 1;
                if(quantità > qLibri){
                    return false;
                }
                else{
                    PreparedStatement pstmUpdate = connessione.prepareStatement("UPDATE CarrelloLibri SET Quantità = ? WHERE Email = ? and ID_Libro = ?");
                    pstmUpdate.setInt(1, quantità);
                    pstmUpdate.setString(2, email);
                    pstmUpdate.setString(3, id_libro);
                    pstmUpdate.executeUpdate();
                    return true;
                }

            }
            else{
                PreparedStatement pstmAdd = connessione.prepareStatement("INSERT INTO CarrelloLibri VALUES(?, ?, ?, 'Acquisto', 'Negozio', ?)");
                pstmAdd.setString(1, id_libro);
                pstmAdd.setString(2, email);
                pstmAdd.setInt(3, 1);
                pstmAdd.setNull(4, Types.NULL);
                System.out.println(pstmAdd);
                pstmAdd.execute();
                
                return true;
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
    public Set<Libro> showBookCart (String email) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstmFind = connessione.prepareStatement("SELECT libri.ID_Libro, libri.Titolo, libri.Autore, libri.Genere, libri.Prezzo, libri.Quantità, carrellolibri.Quantità AS Carrello, carrellolibri.Tipo AS Tipo FROM libri INNER JOIN carrellolibri ON libri.ID_Libro = carrellolibri.ID_Libro AND carrellolibri.Email = ?");
            pstmFind.setString(1, email);
            System.out.println(pstmFind);
            ResultSet rsFind = pstmFind.executeQuery();
            Libro libro = new Libro();
            Set carrelloLibri = new HashSet();
            while(rsFind.next()){
                libro = returnLibro(rsFind);
                libro.setQuantitaCarrello(rsFind.getString("Carrello"));
                libro.setTipoAcquisto(rsFind.getString("Tipo"));
                carrelloLibri.add(libro);
            }
            return carrelloLibri;
        }catch (SQLException ex) {
            return null;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public boolean updateBookCart(String email, String id_libro, int quantita, String tipo) throws ClassNotFoundException {
        Connection connessione = returnConnection.getConnection();
        if(quantita > 0){
            try {
                PreparedStatement pstmUpdate = connessione.prepareStatement("UPDATE carrellolibri SET Quantità = ?, Tipo = ?, Tipo_Consegna = ? WHERE Email = ? and ID_Libro = ?");
                pstmUpdate.setInt(1, quantita);
                pstmUpdate.setString(2, tipo);
                if(tipo.equalsIgnoreCase("Prestito")){
                    pstmUpdate.setString(3, "Negozio");
                }
                else{
                    pstmUpdate.setNull(3, Types.NULL);
                }
                pstmUpdate.setString(4, email);
                pstmUpdate.setString(5, id_libro);
                pstmUpdate.executeUpdate();
                System.out.println(pstmUpdate);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            finally{
                if(connessione != null){
                    try {
                        connessione.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }   
        }
        else{
            try {
                PreparedStatement pstmDelete = connessione.prepareStatement("DELETE FROM carrellolibri WHERE ID_Libro = ? and Email = ?");
                pstmDelete.setString(1, id_libro);
                pstmDelete.setString(2, email);
                pstmDelete.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            finally{
                if(connessione != null){
                    try {
                        connessione.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }   
        }
        
    }
    
    @Override
    public Set<Libro> showCercaLibri(String titolo, String autore, String genere) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstm = connessione.prepareStatement("SELECT * FROM libri WHERE titolo LIKE ? AND autore LIKE ? AND genere LIKE ?");
            pstm.setString(1, "%"+titolo+"%");
            pstm.setString(2, "%"+autore+"%");
            pstm.setString(3, "%"+genere+"%");
            System.out.println(pstm);
            ResultSet rs = pstm.executeQuery();
            Set libreria = new HashSet();
            while(rs.next()){
                Libro libro = returnLibro(rs);
                System.out.println(rs.getString("ID_Libro"));
                libreria.add(libro);
            }
            return libreria;
        }catch (SQLException ex) {
            return null;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public Set<Prestito> showPrestiti(String info, String tipo) throws ClassNotFoundException, SQLException{
        Connection connessione = returnConnection.getConnection();
        Set prestiti = new HashSet();
        if(tipo.equalsIgnoreCase("data")){
            try {
                System.out.println("ENTRA");
                PreparedStatement pstmFind = connessione.prepareStatement("SELECT libri.Titolo, libriprestiti.Email, libriprestiti.Data_Inizio, libriprestiti.Data_Fine, libriprestiti.Quantità, libriprestiti.Tipo_Ritiro, libriprestiti.Tipo_Consegna FROM libriprestiti INNER JOIN libri ON libri.ID_Libro = libriprestiti.ID_Libro WHERE (libriprestiti.Data_Inizio = ? OR libriprestiti.Data_Fine = ?) AND (libriprestiti.Tipo_Ritiro = 'negozio' OR libriprestiti.Tipo_Consegna = 'negozio')");
                pstmFind.setString(1, info);
                pstmFind.setString(2, info);
                
                ResultSet rsFind1 = pstmFind.executeQuery();
                
                while(rsFind1.next()){
                    Prestito prestito = new Prestito();
                    prestito.setTitolo(rsFind1.getString("Titolo"));
                    prestito.setUtente(rsFind1.getString("Email"));
                    prestito.setData_inizio(rsFind1.getString("Data_Inizio"));
                    prestito.setData_fine(rsFind1.getString("Data_Fine"));
                    prestito.setQuantita(rsFind1.getString("Quantità"));
                    prestito.setTipo_ritiro(rsFind1.getString("Tipo_Ritiro"));
                    prestito.setTipo_consegna(rsFind1.getString("Tipo_Consegna"));
                    prestiti.add(prestito);
                }
                pstmFind.close();
                return prestiti;
            } catch (SQLException ex) {
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            finally{
                if(connessione != null){
                    try {
                        connessione.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        else{
            try {
                PreparedStatement pstmFind = connessione.prepareStatement("SELECT libri.Titolo, libriprestiti.Email, libriprestiti.Data_Inizio, libriprestiti.Data_Fine, libriprestiti.Quantità, libriprestiti.Tipo_Ritiro, libriprestiti.Tipo_Consegna FROM libriprestiti INNER JOIN libri ON libri.ID_Libro = libriprestiti.ID_Libro WHERE (libriprestiti.Email LIKE ?) AND (libriprestiti.Tipo_Ritiro = 'negozio' OR libriprestiti.Tipo_Consegna = 'negozio')");
                pstmFind.setString(1, "%"+info+"%");
                System.out.println(pstmFind);
                ResultSet rsFind2 = pstmFind.executeQuery();
                
                while(rsFind2.next()){
                    Prestito prestito = new Prestito();
                    prestito.setTitolo(rsFind2.getString("Titolo"));
                    prestito.setUtente(rsFind2.getString("Email"));
                    prestito.setData_inizio(rsFind2.getString("Data_Inizio"));
                    prestito.setData_fine(rsFind2.getString("Data_Fine"));
                    prestito.setQuantita(rsFind2.getString("Quantità"));
                    prestito.setTipo_ritiro(rsFind2.getString("Tipo_Ritiro"));
                    prestito.setTipo_consegna(rsFind2.getString("Tipo_Consegna"));
                    prestiti.add(prestito);
                }
                pstmFind.close();
                return prestiti;
                
            } catch (SQLException ex) {
                
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            finally{
                if(connessione != null){
                    try {
                        connessione.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    @Override
    public boolean loanLibro(Libro libro, String email) throws ClassNotFoundException, ParseException{
        Connection connessione = returnConnection.getConnection();
        int quantitaFinale = parseInt(libro.getQuantita())- parseInt(libro.getQuantitaCarrello());
        Carrello item_carrello = new Carrello();
        PreparedStatement pstmFind;
        try {
            pstmFind = connessione.prepareStatement("SELECT * FROM carrellolibri WHERE Email = ? AND ID_Libro = ?");
            pstmFind.setString(1, email);
            pstmFind.setString(2, libro.getID_Libro());
            ResultSet rsFind = pstmFind.executeQuery();
            
            while(rsFind.next()){
                item_carrello.setEmail(rsFind.getString("Email"));
                item_carrello.setID_Libro(rsFind.getString("ID_Libro"));
                item_carrello.setTipoAcquisto(rsFind.getString("Tipo"));
                item_carrello.setTipoRitiro(rsFind.getString("Tipo_Ritiro"));
                item_carrello.setTipoConsegna(rsFind.getString("Tipo_Consegna"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (quantitaFinale>0){
            try {
                PreparedStatement pstmUpdate = connessione.prepareStatement("UPDATE libri SET Quantità = ? WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
                pstmUpdate.setInt(1, quantitaFinale);
                pstmUpdate.setString(2, libro.getTitolo());
                pstmUpdate.setString(3, libro.getAutore());
                pstmUpdate.setString(4, libro.getGenere());
                pstmUpdate.setString(5, libro.getPrezzo());
                pstmUpdate.executeUpdate();
                
                PreparedStatement pstmAdd = connessione.prepareStatement("INSERT INTO LibriPrestiti VALUES(?, ?, ?, ?, ?, ?, ?)");
                pstmAdd.setInt(1, parseInt(libro.getID_Libro()));
                pstmAdd.setString(2, email);
                LocalDate inizio_prestito = LocalDate.now();
                pstmAdd.setString(3, inizio_prestito.toString());
                System.out.println(inizio_prestito.toString());
                
                Date data_inizio = new SimpleDateFormat("yyyy-MM-dd").parse(inizio_prestito.toString());
                Calendar cal = Calendar.getInstance();
                cal.setTime(data_inizio);
                cal.add(Calendar.DATE, 31);
                Date data_fine = cal.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String fine_prestito = dateFormat.format(data_fine);
                
                pstmAdd.setString(4, fine_prestito);
                pstmAdd.setInt(5, parseInt(libro.getQuantitaCarrello()));
                pstmAdd.setString(6, item_carrello.getTipoRitiro());
                pstmAdd.setString(7, item_carrello.getTipoConsegna());
                pstmAdd.execute();
                
                PreparedStatement pstmDelete = connessione.prepareStatement("DELETE FROM carrellolibri WHERE ID_Libro = ? AND Email = ?");
                pstmDelete.setString(1, item_carrello.getID_Libro());
                pstmDelete.setString(2, email);
                pstmDelete.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
        }
        else{
            try {
                PreparedStatement pstmDelete = connessione.prepareStatement("DELETE FROM libri WHERE ID_Libro = ?");
                pstmDelete.setString(1, item_carrello.getID_Libro());
                pstmDelete.execute();
                
                PreparedStatement pstmAdd = connessione.prepareStatement("INSERT INTO LibriPrestiti VALUES(?, ?, ?, ?, ?, ?, ?)");
                pstmAdd.setInt(1, parseInt(libro.getID_Libro()));
                pstmAdd.setString(2, email);
                LocalDate inizio_prestito = LocalDate.now();
                pstmAdd.setString(3, inizio_prestito.toString());
                System.out.println(inizio_prestito.toString());
                
                Date data_inizio = new SimpleDateFormat("yyyy-MM-dd").parse(inizio_prestito.toString());
                Calendar cal = Calendar.getInstance();
                cal.setTime(data_inizio);
                cal.add(Calendar.DATE, 31);
                Date data_fine = cal.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String fine_prestito = dateFormat.format(data_fine);
                
                pstmAdd.setString(4, fine_prestito);
                pstmAdd.setInt(5, parseInt(libro.getQuantitaCarrello()));
                pstmAdd.setString(6, item_carrello.getTipoRitiro());
                pstmAdd.setString(7, item_carrello.getTipoConsegna());
                pstmAdd.execute();
                
                PreparedStatement pstmDelete1 = connessione.prepareStatement("DELETE FROM carrellolibri WHERE ID_Libro = ? AND Email = ?");
                pstmDelete1.setString(1, item_carrello.getID_Libro());
                pstmDelete1.setString(2, email);
                pstmDelete1.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    }
    
    public boolean sellLibro(Libro libro, String email) throws ClassNotFoundException, ParseException{
        Connection connessione = returnConnection.getConnection();
        int quantitaFinale = parseInt(libro.getQuantita())- parseInt(libro.getQuantitaCarrello());
        Carrello item_carrello = new Carrello();
        PreparedStatement pstmFind;
        try {
            pstmFind = connessione.prepareStatement("SELECT * FROM carrellolibri WHERE Email = ? AND ID_Libro = ?");
            pstmFind.setString(1, email);
            pstmFind.setString(2, libro.getID_Libro());
            ResultSet rsFind = pstmFind.executeQuery();
            
            while(rsFind.next()){
                
                item_carrello.setEmail(rsFind.getString("Email"));
                item_carrello.setID_Libro(rsFind.getString("ID_Libro"));
                System.out.println("ID_Libro:"+item_carrello.getID_Libro());
                item_carrello.setTipoAcquisto(rsFind.getString("Tipo"));
                item_carrello.setTipoRitiro(rsFind.getString("Tipo_Ritiro"));
                System.out.println("TIPO_RITIRO:"+item_carrello.getTipoRitiro());
                item_carrello.setTipoConsegna(rsFind.getString("Tipo_Consegna"));
                System.out.println("TIPO_CONSEGNA:"+item_carrello.getTipoConsegna());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (quantitaFinale>0){
            try {
                PreparedStatement pstmUpdate = connessione.prepareStatement("UPDATE libri SET Quantità = ? WHERE Titolo = ? and Autore = ? and Genere = ? and Prezzo = ?");
                pstmUpdate.setInt(1, quantitaFinale);
                pstmUpdate.setString(2, libro.getTitolo());
                pstmUpdate.setString(3, libro.getAutore());
                pstmUpdate.setString(4, libro.getGenere());
                pstmUpdate.setString(5, libro.getPrezzo());
                pstmUpdate.executeUpdate();
                
                PreparedStatement pstmAdd = connessione.prepareStatement("INSERT INTO LibriAcquistati VALUES(?, ?, ?, ?, ?, ?)");
                pstmAdd.setInt(1, parseInt(libro.getID_Libro()));
                pstmAdd.setString(2, email);
                pstmAdd.setString(3, libro.getPrezzo());
                pstmAdd.setInt(4, parseInt(libro.getQuantitaCarrello()));
                
                LocalDate data_acquisto = LocalDate.now();
                pstmAdd.setString(5, data_acquisto.toString());
                
                pstmAdd.setString(6, item_carrello.getTipoRitiro());
                pstmAdd.execute();
                
                PreparedStatement pstmDelete = connessione.prepareStatement("DELETE FROM carrellolibri WHERE ID_Libro = ? AND Email = ?");
                pstmDelete.setString(1, item_carrello.getID_Libro());
                pstmDelete.setString(2, email);
                pstmDelete.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
        }
        else{
            try {
                PreparedStatement pstmUpdate = connessione.prepareStatement("UPDATE libri SET Quantità = ? WHERE ID_Libro = ?");
                pstmUpdate.setInt(1, quantitaFinale);
                pstmUpdate.setString(2, item_carrello.getID_Libro());
                System.out.println(pstmUpdate);
                pstmUpdate.executeUpdate();
                
                PreparedStatement pstmAdd = connessione.prepareStatement("INSERT INTO LibriAcquistati VALUES(?, ?, ?, ?, ?, ?)");
                pstmAdd.setInt(1, parseInt(libro.getID_Libro()));
                pstmAdd.setString(2, email);
                pstmAdd.setString(3, libro.getPrezzo());
                pstmAdd.setInt(4, parseInt(libro.getQuantitaCarrello()));
                
                LocalDate data_acquisto = LocalDate.now();
                pstmAdd.setString(5, data_acquisto.toString());
                
                pstmAdd.setString(6, item_carrello.getTipoRitiro());
                pstmAdd.execute();
                
                PreparedStatement pstmDelete1 = connessione.prepareStatement("DELETE FROM carrellolibri WHERE ID_Libro = ? AND Email = ?");
                pstmDelete1.setString(1, item_carrello.getID_Libro());
                pstmDelete1.setString(2, email);
                pstmDelete1.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    }
    
    public Set<Carrello> showCheckout(String email) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstmFind = connessione.prepareStatement("SELECT carrellolibri.ID_Libro, libri.Titolo, carrellolibri.Quantità, carrellolibri.Tipo, carrellolibri.Tipo_Ritiro, carrellolibri.Tipo_Consegna FROM carrellolibri INNER JOIN libri ON libri.ID_Libro = carrellolibri.ID_Libro AND carrellolibri.Email = ?");
            pstmFind.setString(1, email);
            System.out.println(pstmFind);
            ResultSet rsFind = pstmFind.executeQuery();
            
            Set checkout = new HashSet();
            
            while(rsFind.next()){
                Carrello carrello = new Carrello();
                carrello.setID_Libro(rsFind.getString("ID_Libro"));
                carrello.setTitolo(rsFind.getString("Titolo"));
                carrello.setQuantita(rsFind.getString("Quantità"));
                carrello.setTipoAcquisto(rsFind.getString("Tipo"));
                carrello.setTipoRitiro(rsFind.getString("Tipo_Ritiro"));
                carrello.setTipoConsegna(rsFind.getString("Tipo_Consegna"));
                checkout.add(carrello);
            }
            return checkout;
            
        }catch (SQLException ex) {
            return null;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public boolean updateCheckout(String email, String id_libro, String tipo_ritiro, String tipo_consegna) throws ClassNotFoundException {
        Connection connessione = returnConnection.getConnection();
        try {
            PreparedStatement pstmUpdate = connessione.prepareStatement("UPDATE carrellolibri SET Tipo_Ritiro = ?, Tipo_Consegna = ? WHERE ID_Libro = ? AND Email = ?");
            pstmUpdate.setString(1, tipo_ritiro);
            if(tipo_consegna.equalsIgnoreCase("null")){
                pstmUpdate.setNull(2, Types.NULL);
            }
            else{
                pstmUpdate.setString(2, tipo_consegna);
            }
            pstmUpdate.setString(3, id_libro);
            pstmUpdate.setString(4, email);
            System.out.println(pstmUpdate);
            pstmUpdate.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Set<Prestito> showPrestitiUtente (String email) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        Set prestiti = new HashSet();
        try {
            PreparedStatement pstmFind = connessione.prepareStatement("SELECT libri.Titolo, libriprestiti.Email, libriprestiti.Data_Inizio, libriprestiti.Data_Fine, libriprestiti.Quantità, libriprestiti.Tipo_Ritiro, libriprestiti.Tipo_Consegna FROM libriprestiti INNER JOIN libri ON libri.ID_Libro = libriprestiti.ID_Libro WHERE libriprestiti.Email = ?");
            pstmFind.setString(1, email);
            System.out.println(pstmFind);
            ResultSet rsFind2 = pstmFind.executeQuery();
            
            while(rsFind2.next()){
                Prestito prestito = new Prestito();
                prestito.setTitolo(rsFind2.getString("Titolo"));
                prestito.setUtente(rsFind2.getString("Email"));
                prestito.setData_inizio(rsFind2.getString("Data_Inizio"));
                prestito.setData_fine(rsFind2.getString("Data_Fine"));
                prestito.setQuantita(rsFind2.getString("Quantità"));
                prestito.setTipo_ritiro(rsFind2.getString("Tipo_Ritiro"));
                prestito.setTipo_consegna(rsFind2.getString("Tipo_Consegna"));
                prestiti.add(prestito);
            }
            pstmFind.close();
            return prestiti;
        } catch (SQLException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public Set<Prestito> showAcquistiUtente (String email) throws ClassNotFoundException{
        Connection connessione = returnConnection.getConnection();
        Set prestiti = new HashSet();
        try {
            PreparedStatement pstmFind = connessione.prepareStatement("SELECT libri.Titolo, libriacquistati.Email, libriacquistati.Quantità, libriacquistati.Data_Acquisto, libriacquistati.Tipo_Ritiro FROM libriacquistati INNER JOIN libri ON libri.ID_Libro = libriacquistati.ID_Libro WHERE libriacquistati.Email = ?");
            pstmFind.setString(1, email);
            System.out.println(pstmFind);
            ResultSet rsFind2 = pstmFind.executeQuery();
            
            while(rsFind2.next()){
                Prestito prestito = new Prestito();
                prestito.setTitolo(rsFind2.getString("Titolo"));
                prestito.setUtente(rsFind2.getString("Email"));
                prestito.setData_inizio(rsFind2.getString("Data_Acquisto"));
                prestito.setQuantita(rsFind2.getString("Quantità"));
                prestito.setTipo_ritiro(rsFind2.getString("Tipo_Ritiro"));
                prestiti.add(prestito);
            }
            pstmFind.close();
            return prestiti;
        } catch (SQLException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            if(connessione != null){
                try {
                    connessione.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public Set<Prestito> showAcquisti (String info, String tipo) throws ClassNotFoundException, SQLException{
        Connection connessione = returnConnection.getConnection();
        Set acquisti = new HashSet();
        if(tipo.equalsIgnoreCase("data")){
            try {
                PreparedStatement pstmFind = connessione.prepareStatement("SELECT libri.Titolo, libriacquistati.Email, libriacquistati.Data, libriacquistati.Quantità, libriacquistati.Tipo_Ritiro FROM libriacquistati INNER JOIN libri ON libri.ID_Libro = libriacquistati.ID_Libro WHERE libriacquistati.Data = ? AND libriacquistati.Tipo_Ritiro = 'negozio'");
                pstmFind.setString(1, info);
                
                ResultSet rsFind1 = pstmFind.executeQuery();
                
                while(rsFind1.next()){
                    Prestito acquisto = new Prestito();
                    acquisto.setTitolo(rsFind1.getString("Titolo"));
                    acquisto.setUtente(rsFind1.getString("Email"));
                    acquisto.setData_inizio(rsFind1.getString("Data"));
                    acquisto.setQuantita(rsFind1.getString("Quantità"));
                    acquisto.setTipo_ritiro(rsFind1.getString("Tipo_Ritiro"));
                    acquisti.add(acquisto);
                }
                pstmFind.close();
                return acquisti;
            } catch (SQLException ex) {
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            finally{
                if(connessione != null){
                    try {
                        connessione.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        else{
            try {
                PreparedStatement pstmFind = connessione.prepareStatement("SELECT libri.Titolo, libriacquistati.Email, libriacquistati.Data_Acquisto, libriacquistati.Quantità, libriacquistati.Tipo_Ritiro FROM libriacquistati INNER JOIN libri ON libri.ID_Libro = libriacquistati.ID_Libro WHERE libriacquistati.Email LIKE ? AND libriacquistati.Tipo_Ritiro = 'negozio'");
                pstmFind.setString(1, "%"+info+"%");
                System.out.println(pstmFind);
                ResultSet rsFind2 = pstmFind.executeQuery();
                
                while(rsFind2.next()){
                    Prestito acquisto = new Prestito();
                    acquisto.setTitolo(rsFind2.getString("Titolo"));
                    acquisto.setUtente(rsFind2.getString("Email"));
                    acquisto.setData_inizio(rsFind2.getString("Data_Acquisto"));
                    acquisto.setQuantita(rsFind2.getString("Quantità"));
                    acquisto.setTipo_ritiro(rsFind2.getString("Tipo_Ritiro"));
                    acquisti.add(acquisto);
                }
                pstmFind.close();
                return acquisti;
                
            } catch (SQLException ex) {
                
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            finally{
                if(connessione != null){
                    try {
                        connessione.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}

