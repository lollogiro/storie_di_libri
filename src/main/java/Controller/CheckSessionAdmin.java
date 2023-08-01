package Controller;

import DAO.readJsonParameter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "CheckSessionAdmin", urlPatterns = {"/CheckSessionAdmin"})
public class CheckSessionAdmin extends HttpServlet {

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
        boolean esito = false;
        HttpSession session = request.getSession(false);
        if(session == null){
            esito = false;
        }
        else{
            String task = (String) session.getAttribute("task");
            if(task.equalsIgnoreCase("bookseller")){
                esito = true;
            }
            else{
                esito = false;
            }
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
