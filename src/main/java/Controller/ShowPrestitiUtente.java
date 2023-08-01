/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.LibroDAO;
import static DAO.readJsonParameter.getJsonParameter;
import Model.Prestito;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author LUCA
 */
@WebServlet(name = "ShowPrestitiUtente", urlPatterns = {"/ShowPrestitiUtente"})
public class ShowPrestitiUtente extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowPrestitiUtente</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowPrestitiUtente at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LibroDAO db = new LibroDAO();
        Set<Prestito> select = new HashSet<Prestito>();
        ArrayList <Prestito> list = new ArrayList();
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        try {
            select = db.showPrestitiUtente(email);
            if(select != null){
                Iterator i = select.iterator();
                while(i.hasNext()){
                    list.add((Prestito)i.next());
                }
            }
            else{
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowPrestitiUtente.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
