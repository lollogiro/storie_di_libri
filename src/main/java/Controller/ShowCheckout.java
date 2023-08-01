package Controller;

import DAO.LibroDAO;
import Model.Carrello;
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
import javax.servlet.http.HttpSession;


@WebServlet(name = "ShowCheckout", urlPatterns = {"/ShowCheckout"})
public class ShowCheckout extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response, ArrayList<Carrello> list)
            throws ServletException, IOException {
        response.setContentType("application/json;");
        JsonWriter w = new JsonWriter(response.getWriter());            
        w.beginObject();
        w.name("checkout");
            w.beginArray();
            for(int i=0; i<list.size(); i++){
                w.beginObject();
                    w.name("id_libro").value(list.get(i).getID_Libro());
                    w.name("titolo").value(list.get(i).getTitolo());
                    w.name("quantita").value(list.get(i).getQuantita());
                    w.name("tipo_acquisto").value(list.get(i).getTipoAcquisto());
                    w.name("tipo_ritiro").value(list.get(i).getTipoRitiro());
                    w.name("tipo_consegna").value(list.get(i).getTipoConsegna());
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
        Carrello carrello = new Carrello();
        ArrayList <Carrello> list = new ArrayList();
        Set <Carrello> item_carrello = new HashSet<Carrello>();
        LibroDAO db = new LibroDAO();
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        try {
            item_carrello = db.showCheckout(email);
            if(item_carrello != null){
                Iterator i = item_carrello.iterator();
                while(i.hasNext()){
                    list.add((Carrello)i.next());
                    
                }
            }  
            else{
            }
                
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowCheckout.class.getName()).log(Level.SEVERE, null, ex);
        }
        processRequest(request, response, list);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Carrello> list = null;
        processRequest(request, response, list);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
