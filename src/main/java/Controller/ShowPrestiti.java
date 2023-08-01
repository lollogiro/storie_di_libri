package Controller;

import DAO.LibroDAO;
import static DAO.readJsonParameter.getJsonParameter;
import Model.Prestito;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet(name = "ShowPrestiti", urlPatterns = {"/ShowPrestiti"})
public class ShowPrestiti extends HttpServlet {


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
        Set<Prestito> select = new HashSet<Prestito>();
        ArrayList <Prestito> list = new ArrayList();
        if(json.get("email").getAsString().equalsIgnoreCase("null")){
            String data = json.get("data").getAsString();
            System.out.println("data:"+data);
            try {
                select = db.showPrestiti(data, "data");
                if(select != null){
                    Iterator i = select.iterator();
                    while(i.hasNext()){
                        list.add((Prestito)i.next());
                    }
                }
                else{
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ShowPrestiti.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            String email = json.get("email").getAsString();
            try {
                select = db.showPrestiti(email, "email");
                if(select != null){
                    Iterator i = select.iterator();
                    while(i.hasNext()){
                        list.add((Prestito)i.next());
                    }
                }
                else{
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ShowPrestiti.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        response.setContentType("application/json;");
        JsonWriter w = new JsonWriter(response.getWriter());


        w.beginObject();
        w.name("prestiti");
        w.beginArray();
        for(int i=0; i<list.size(); i++){
            w.beginObject();
                w.name("titolo").value(list.get(i).getTitolo());
                w.name("utente").value(list.get(i).getUtente());
                w.name("data_inizio").value(list.get(i).getData_inizio());
                w.name("data_fine").value(list.get(i).getData_fine());
                w.name("quantita").value(list.get(i).getQuantita());
                w.name("tipo_ritiro").value(list.get(i).getTipo_ritiro());
                w.name("tipo_consegna").value(list.get(i).getTipo_consegna());
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
