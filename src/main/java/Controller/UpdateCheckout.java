package Controller;

import DAO.LibroDAO;
import DAO.readJsonParameter;
import static DAO.readJsonParameter.getJsonParameter;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "UpdateCheckout", urlPatterns = {"/UpdateCheckout"})
public class UpdateCheckout extends HttpServlet {


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
        
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        String id_libro = json.get("id_libro").getAsString();
        String tipo_ritiro = json.get("tipo_ritiro").getAsString();
        String tipo_consegna = json.get("tipo_consegna").getAsString();
        System.out.println("Tipo_Consegna:"+tipo_consegna);

        boolean esito = false;
        
        try {
            esito = db.updateCheckout(email, id_libro, tipo_ritiro, tipo_consegna);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateCheckout.class.getName()).log(Level.SEVERE, null, ex);
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
