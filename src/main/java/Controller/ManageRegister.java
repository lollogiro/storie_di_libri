package Controller;

import DAO.UtenteDAO;
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

@WebServlet(name = "ManageRegister", urlPatterns = {"/ManageRegister"})
public class ManageRegister extends HttpServlet {

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
                UtenteDAO db = new UtenteDAO();
        JsonObject json = getJsonParameter(request);
        String email = json.get("email").getAsString();
        String esito = "";       
        
        try {
            esito = db.controlData(email);
            if(esito == "ok"){
                String nome = json.get("nome").getAsString();
                String cognome = json.get("cognome").getAsString();
                String telefono = json.get("telefono").getAsString();
                String via = json.get("via").getAsString();
                String civico = json.get("civico").getAsString();
                String citta = json.get("citta").getAsString();
                String provincia = json.get("provincia").getAsString();
                String cap = json.get("cap").getAsString();
                String password = json.get("password").getAsString();
                int negoziante = 0;
                
                boolean insert = db.insertData(nome, cognome, email, telefono, via, civico, citta, provincia, cap, negoziante, password);
                if(insert == false){
                    esito = "err";
                }
            }
            else{
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManageRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/json");
        response.getWriter().print(readJsonParameter.printJson(esito));
        response.flushBuffer();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
