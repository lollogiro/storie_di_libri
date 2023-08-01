package Controller;

import DAO.LibroDAO;
import DAO.readJsonParameter;
import Model.Libro;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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


@WebServlet(name = "Checkout", urlPatterns = {"/Checkout"})
public class Checkout extends HttpServlet {


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
        Libro libro = new Libro();
        ArrayList <Libro> list = new ArrayList();
        Set <Libro> libri = new HashSet<Libro>();
        LibroDAO db = new LibroDAO();
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        //System.out.println(email);
        try {
            libri = db.showBookCart(email);
            if(libri != null){
                Iterator i = libri.iterator();
                while(i.hasNext()){
                    list.add((Libro)i.next());
                    
                }
            }  
            else{
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        boolean esito = false;
        
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getTipoAcquisto().equals("Prestito")){
                libro.setID_Libro(list.get(i).getID_Libro());
                libro.setTitolo(list.get(i).getTitolo());
                libro.setAutore(list.get(i).getAutore());
                libro.setGenere(list.get(i).getGenere());
                libro.setPrezzo(list.get(i).getPrezzo());
                libro.setQuantita(list.get(i).getQuantita());
                libro.setQuantitaCarrello(list.get(i).getQuantitaCarrello());
                libro.setTipoAcquisto(list.get(i).getTipoAcquisto());
                try {
                    esito = db.loanLibro(libro, email);
                } catch (ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                libro.setID_Libro(list.get(i).getID_Libro());
                libro.setTitolo(list.get(i).getTitolo());
                libro.setAutore(list.get(i).getAutore());
                libro.setGenere(list.get(i).getGenere());
                libro.setPrezzo(list.get(i).getPrezzo());
                libro.setQuantita(list.get(i).getQuantita());
                libro.setQuantitaCarrello(list.get(i).getQuantitaCarrello());
                libro.setTipoAcquisto(list.get(i).getTipoAcquisto());
                
                try {
                    esito = db.sellLibro(libro, email);
                } catch (ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
                }
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
