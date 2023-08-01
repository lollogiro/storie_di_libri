package Controller;

import DAO.LibroDAO;
import Model.Libro;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
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

@WebServlet(name = "ShowLibri", urlPatterns = {"/ShowLibri"})
public class ShowLibri extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response, ArrayList<Libro> list)
            throws ServletException, IOException {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Libro libro = new Libro();
        ArrayList <Libro> list = new ArrayList();
        Set <Libro> libri = new HashSet<Libro>();
        LibroDAO db = new LibroDAO();
        
        try {
            libri = db.showLibri();
            if(libri != null){
                Iterator i = libri.iterator();
                while(i.hasNext()){
                    list.add((Libro)i.next());
                }
            }
            else{
            }
                
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowLibri.class.getName()).log(Level.SEVERE, null, ex);
        }
        processRequest(request, response, list);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Libro> list = null;
        processRequest(request, response, list);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
