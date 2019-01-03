/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.controller.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.model.BackendContainer;
import pl.polsl.model.queryHistory.SingleQuery;
import pl.polsl.model.querydb.DBManagerIniException;
import pl.polsl.model.querydb.QueryDBManager;

/** Service that returns to client info about already made queries during server uptime.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.1
 */
public class HistoryRetrieverServlet extends HttpServlet {
    /**
     * Reference to instance of object storing all functionalities connected with this server's database.
     */
    private QueryDBManager queryDBManager;
    
    public HistoryRetrieverServlet()
    {
        try
        {
            queryDBManager = QueryDBManager.getInstance();
        }
        catch(DBManagerIniException ex)
        {
            queryDBManager = null;
            System.out.println("Could not initialize the database manager! " + ex.getMessage());
        }
    }
    /**
     * Prints the sent queries to the output, using the <br> separator.
     * @param queries Queries to send to client.
     * @param out Stream that sends data to client.
     */
    private void printQueries(List<SingleQuery> queries, PrintWriter out)
    {
        for(SingleQuery query : queries)
        {
            query.setLineSeparator("<br>");
            out.println(query.toString());
        }
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Integral calculator query history</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Requested information about queries made by you during session uptime:</h1>");
            out.println();
            out.println();
            if(queryDBManager != null)
            {
                String sessionID = request.getSession().getId();
                Connection dbConnection = queryDBManager.getDBConnection();
                List<SingleQuery> queriesMadeByClient = queryDBManager.getSelector().selectData(dbConnection, sessionID);
                printQueries(queriesMadeByClient, out);
            }
            else
            {
                out.println("Error - could not establish connection with the database. Please try again later.");
            }
            
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
