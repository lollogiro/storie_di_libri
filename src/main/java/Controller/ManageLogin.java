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
import javax.servlet.http.HttpSession;

@WebServlet(name = "ManageLogin", urlPatterns = {"/ManageLogin"})
public class ManageLogin extends HttpServlet {

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
        String esito = "";
        System.out.println(request.getSession(false));
        if(request.getSession(false) == null){
            UtenteDAO db = new UtenteDAO();
            JsonObject json = getJsonParameter(request);
            String email = json.get("email").getAsString();
            String password = json.get("password").getAsString();
            boolean esito1=false, esito2=false;

            try {
                esito1 = db.checkLogin(email, password);
                if(esito1){
                    //inizia sessione utente normale
                    HttpSession session = request.getSession();
                    session.setAttribute("email",email);
                    session.setAttribute("task", "normal_user");
                    esito = Boolean.toString(esito1);

                }
                else{
                    esito2 = db.checkLoginAdmin(email, password);
                    System.out.println("esito2:"+esito2);
                    if(esito2){
                        //inizia sessione utente negoziante
                        HttpSession session = request.getSession();
                        session.setAttribute("email",email);
                        session.setAttribute("task", "bookseller");
                        esito = Boolean.toString(esito2);

                    }
                    else{
                        esito = "false";
                        System.out.println("Entra qui");

                    }
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ManageLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        }
        else{
            esito = "alreadyLogged";    
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
