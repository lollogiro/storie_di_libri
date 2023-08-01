/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.LibroDAO;
import DAO.readJsonParameter;
import static DAO.readJsonParameter.getJsonParameter;
import Model.Libro;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SellLibro", urlPatterns = {"/SellLibro"})
public class SellLibro extends HttpServlet {

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
            esito = db.updateLibro(libro);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddLibro.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/json");
        response.getWriter().print(readJsonParameter.printJson(esito));
        response.flushBuffer();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
