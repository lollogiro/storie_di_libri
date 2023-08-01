/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.LibroDAO;
import static DAO.readJsonParameter.getJsonParameter;
import Model.Libro;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ShowCercaLibro", urlPatterns = {"/ShowCercaLibro"})
public class ShowCercaLibro extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LibroDAO db = new LibroDAO();
        JsonObject json = getJsonParameter(request);
        String titolo = json.get("titolo").getAsString();
        String autore = json.get("autore").getAsString();
        String genere = json.get("genere").getAsString();
        Libro libro = new Libro();
        ArrayList <Libro> list = new ArrayList();
        Set <Libro> libri = new HashSet<Libro>();

        try {
            libri = db.showCercaLibri(titolo, autore, genere);
            if(libri != null){
                Iterator i = libri.iterator();
                while(i.hasNext()){
                    list.add((Libro)i.next());
                }
            }
            else{
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowCercaLibro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(list.size());
        response.setContentType("application/json;");
        JsonWriter w = new JsonWriter(response.getWriter());            
        w.beginObject();
        w.name("libri");
            w.beginArray();
            for(int i=0; i<list.size(); i++){
                w.beginObject();
                    w.name("id_libro").value(list.get(i).getID_Libro());
                    w.name("titolo").value(list.get(i).getTitolo());
                    w.name("autore").value(list.get(i).getAutore());
                    w.name("genere").value(list.get(i).getGenere());
                    w.name("prezzo").value(list.get(i).getPrezzo());
                    w.name("quantita").value(list.get(i).getQuantita());
                w.endObject();
            }  
            w.endArray();
        w.endObject();
        w.close();
        response.flushBuffer();
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
