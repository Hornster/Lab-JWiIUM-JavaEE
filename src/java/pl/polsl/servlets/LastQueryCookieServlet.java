/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.model.BackendContainer;
import pl.polsl.model.PredefinedCommunicates;
import pl.polsl.model.exceptions.NoQueryFoundException;
import pl.polsl.model.queryHistory.SingleQuery;
import pl.polsl.utility.dataCheck.ParseModifyString;

/**Servlet that accepts request for last made by the user query.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0*/
public class LastQueryCookieServlet extends HttpServlet {
    /**
     * Name for the cookie that stores ID of last query made by the user.
     */
    public static final String lastQueryCookieName = "lastQueryIndicator";
    /**
     * Reference to the BackendContainer singleton
     */
    private BackendContainer backendContainer;
   
    public LastQueryCookieServlet()
    {
        backendContainer = BackendContainer.getInstance();
    }
    /**
     * If possible, retrieves query under index passed as value String.
     * @param value Value, for example from a cookie.
     * @return Last query or information of error that occured and made retrieving the query impossible.
     */
    private String retrieveUserQuery(String value)
    {
        try
        {
            if(ParseModifyString.tryStringToInt(value))
            {
                int queryIndex = Integer.parseInt(value);
                SingleQuery requestedQuery = backendContainer.queryManager.getQuery(queryIndex);
                requestedQuery.setLineSeparator("<br />");
                
                return requestedQuery.toString();
            }
            else
            {
                return PredefinedCommunicates.cookieDataCorrupted();
            }
        }
        catch(NoQueryFoundException ex)
        {
            return ex.getMessage();
        }
    }
    /**
     * Searches of client provided a cookie indicating that they have already made a query before.
     * If such cookie is found - the last query is retrieved from the history.
     * @param cookies
     * @return 
     */
    private String chkForLastQuery(Cookie[] cookies)
    {
        if(cookies == null)
            return PredefinedCommunicates.noQueriesMadeYet();
        
        for(Cookie cookie : cookies)
        {
            if(cookie.getName().equals(lastQueryCookieName))
            {
                return retrieveUserQuery(cookie.getValue());
            }
        }
        //If no last query cookie was found - user made no queries during the session yet.
        return PredefinedCommunicates.noQueriesMadeYet();
    }
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
            Cookie[] cookies = request.getCookies();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Last user query</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Last query made by you: <br> </h1>");
            out.println(chkForLastQuery(cookies));
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
        processRequest(request, response);
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
