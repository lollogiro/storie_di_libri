package Controller;

import DAO.LibroDAO;
import Model.Libro;
import DAO.readJsonParameter;
import static DAO.readJsonParameter.getJsonParameter;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddLibro", urlPatterns = {"/AddLibro"})
public class AddLibro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        LibroDAO db = new LibroDAO();
        Libro libro;
        String esito ="";
        JsonObject json = getJsonParameter(request);
        
        String titolo = json.get("titolo").getAsString();
        String autore = json.get("autore").getAsString();
        String genere = json.get("genere").getAsString();
        String prezzo = json.get("prezzo").getAsString();
        String quantita = json.get("quantita").getAsString();
        String carrello = null;
        String tipo = null;
        
        libro = new Libro(titolo, autore, genere, prezzo, quantita, carrello, tipo);
        
        try {
            esito = db.addLibro(libro);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddLibro.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(esito);
        response.setContentType("application/json");
        response.getWriter().print(readJsonParameter.printJson(esito));
        response.flushBuffer();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
