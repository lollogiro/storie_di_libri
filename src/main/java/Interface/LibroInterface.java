package Interface;

import Model.Libro;
import java.text.ParseException;
import java.util.Set;

public interface LibroInterface {
    Set <Libro> showLibri() throws ClassNotFoundException;
    String addLibro(Libro libro) throws ClassNotFoundException;
    String removeLibro(String titolo, String autore, String genere, String prezzo) throws ClassNotFoundException;
    String updateLibro(Libro libro) throws ClassNotFoundException;
    boolean checkPrestito (Libro libro, String email) throws ClassNotFoundException;
    boolean loanLibro(Libro libro, String email) throws ClassNotFoundException, ParseException;
    public boolean addBookToCart (String email, String id_libro) throws ClassNotFoundException;
    public boolean updateBookCart (String email, String id_libro, int quantita, String tipo) throws ClassNotFoundException;
    public Set<Libro> showBookCart (String email) throws ClassNotFoundException;
    public Set<Libro> showCercaLibri(String titolo, String autore, String genere) throws ClassNotFoundException;
}