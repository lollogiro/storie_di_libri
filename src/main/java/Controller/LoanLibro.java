package Controller;

import DAO.LibroDAO;
import DAO.readJsonParameter;
import static DAO.readJsonParameter.getJsonParameter;
import Model.Libro;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoanLibro", urlPatterns = {"/LoanLibro"})
public class LoanLibro extends HttpServlet {

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
            Libro libro = new Libro();
            
            JsonObject json = getJsonParameter(request);
            String titolo = json.get("titolo").getAsString();
            String autore = json.get("autore").getAsString();
            String genere = json.get("genere").getAsString();
            String prezzo = json.get("prezzo").getAsString();
            libro.setTitolo(titolo);
            libro.setAutore(autore);
            libro.setGenere(genere);
            libro.setPrezzo(prezzo);
            libro.setQuantita("0");
            boolean esito = false;
            System.out.println(libro.getID_Libro());
            System.out.println(libro.getAutore());
            
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");
            System.out.println(email);
        try {
            esito = db.checkPrestito(libro, email);
            if(esito){
                System.out.println("esito check prestito:"+esito);
                boolean loan = db.loanLibro(libro, email);
                System.out.println(loan);
                if(loan == false){
                }
                else{
                    esito = true;
                }
            }
            else{}
        } catch (ClassNotFoundException | ParseException ex) {
            Logger.getLogger(LoanLibro.class.getName()).log(Level.SEVERE, null, ex);
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
