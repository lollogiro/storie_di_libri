/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.readJsonParameter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProfileLogout", urlPatterns = {"/ProfileLogout"})
public class ProfileLogout extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession(false)!= null){
            HttpSession session = request.getSession(false);
            session.invalidate();
        
            response.setHeader("Cache-Control","no-store"); //HTTP 1.1 
            response.setHeader("Pragma","no-cache"); //HTTP 1.0 
            response.setDateHeader ("Expires", 0);
            response.sendRedirect("http:/localhost:8080/negozio_libri_RESTFUL/");
            
            response.getWriter().print(readJsonParameter.printJson("success"));

        }
        else{
            response.getWriter().print(readJsonParameter.printJson("error"));
        }
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
