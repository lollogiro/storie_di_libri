/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.LibroDAO;
import Model.Prestito;
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


@WebServlet(name = "ShowAcquistiUtente", urlPatterns = {"/ShowAcquistiUtente"})
public class ShowAcquistiUtente extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LibroDAO db = new LibroDAO();
        Set<Prestito> select = new HashSet<Prestito>();
        ArrayList <Prestito> list = new ArrayList();
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        try {
            select = db.showAcquistiUtente(email);
            if(select != null){
                Iterator i = select.iterator();
                while(i.hasNext()){
                    list.add((Prestito)i.next());
                }
            }
            else{
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowAcquistiUtente.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/json;");
        JsonWriter w = new JsonWriter(response.getWriter());


        w.beginObject();
        w.name("acquisti");
        w.beginArray();
        for(int i=0; i<list.size(); i++){
            w.beginObject();
                w.name("titolo").value(list.get(i).getTitolo());
                w.name("utente").value(list.get(i).getUtente());
                w.name("data_inizio").value(list.get(i).getData_inizio());
                w.name("quantita").value(list.get(i).getQuantita());
                w.name("tipo_ritiro").value(list.get(i).getTipo_ritiro());
            w.endObject();
        }
        w.endArray();
        w.endObject();
        w.close();
        response.flushBuffer();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
