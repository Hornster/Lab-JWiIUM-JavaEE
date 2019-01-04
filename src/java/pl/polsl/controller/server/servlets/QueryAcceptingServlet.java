/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.controller.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.model.BackendContainer;
import pl.polsl.model.CalculationData;
import pl.polsl.model.IntegralData;
import pl.polsl.model.PredefinedCommunicates;
import pl.polsl.model.ServerCommand;
import pl.polsl.model.exceptions.IntegralCalculationException;
import pl.polsl.model.queryHistory.CalcResultListener;
import pl.polsl.model.queryHistory.SingleQuery;
import pl.polsl.controller.server.CommandParser;
import pl.polsl.controller.server.CommandWrapper;
import pl.polsl.model.querydb.DBManagerIniException;
import pl.polsl.model.querydb.QueryDBManager;

/** Servlet that accepts calculation queries from clients, informs them about errors and returns results.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.2
 */
public class QueryAcceptingServlet extends HttpServlet implements CalcResultListener {
    /**
     * Stores ID given by the database to the user that has recently made a query.
     */
    private int lastInnerSessionID;
    /**
     * Stores ID of last made query.
     */
    private int lastMadeQueryID;
    /**
     * Stores the object of last serviced by this servlet query.
     */
    private SingleQuery lastQuery;
    /**
     * Reference to the backendContainer - singleton that stores the essential classes of the server's functionality.
     */
    private BackendContainer backendContainer;
    /**
     * Reference to class that contains other classes that maintain and communicate wih the database
     */
    private QueryDBManager queryDBManager;
    /**
     * Creates commands out of parameters that are parsed later on.
     */
    private CommandWrapper commandWrapper = new CommandWrapper();
    
    /**
     * Parses passed parameters using info about command which they belong to.
     * @param parameters List of parameters.
     * @param parameterType Type of command which the parameters are for, for example SET_INTEGRAL.
     * @return ServerCommand instance with info about parsed parameters. If something was not right -
     * @return commandType field in ServerCommand will be set to INCORRECT.
     */
    private ServerCommand parseIntegralData(List<String> parameters, CommandParser.commandType parameterType)
    {
        String command;
        commandWrapper.newArgList();
        for(String str : parameters)
        {
            commandWrapper.addArgument(str);
        }
        command = commandWrapper.createCommand(parameterType);
        
        return backendContainer.commandParser.ParseCommand(command);
    }
    /**
     * Adds a query to the database. If session connected with the query is not present - it will be added too.
     * @param sessionID ID of the session.
     */
    private void addDataToDB(String sessionID)
    {
        Connection dbConnection = queryDBManager.getDBConnection();
        queryDBManager.getInserter().insertData(dbConnection, sessionID);
        lastInnerSessionID = queryDBManager.getSelector().getInternalSessionID(dbConnection, sessionID);
        
        lastMadeQueryID = queryDBManager.getSelector().getLastQueryID(dbConnection, lastInnerSessionID);
        
        queryDBManager.getInserter().insertData(dbConnection, lastInnerSessionID, lastQuery);
    }
    /**
     * Reads parameters from passed request.
     * @param request Source of parameters.
     * @return String with processed by the server data.
     */
    private String readParams(HttpServletRequest request)
    {
        ServerCommand parsingResult;
        List<String> commandData = new LinkedList<>();
        //First, the SET_INTEGRAL command parameters (formula, begin and end of range)
        commandData.add(request.getParameter("integralFormula"));
        commandData.add(request.getParameter("calcRangeBegin"));
        commandData.add(request.getParameter("calcRangeEnd"));
        parsingResult = parseIntegralData(commandData, CommandParser.commandType.SET_INTEGRAL);
        if(parsingResult.getCommandType() == CommandParser.commandType.INCORRECT)
            return parsingResult.toString();
        backendContainer.integralCalculator.setFunction(parsingResult);
        backendContainer.integralCalculator.assignNewIntegralRange(parsingResult);
        
        //Second, if SET_INTEGRAL parameters were correct, try to parse the SET_METHOD command
        commandData.clear();
        commandData.add(request.getParameter("calcMethod"));
        commandData.add(request.getParameter("calcAccuracy"));
        parsingResult = parseIntegralData(commandData, CommandParser.commandType.SET_METHOD);
        if(parsingResult.getCommandType() == CommandParser.commandType.INCORRECT)
            return parsingResult.toString();
        
        try
        {
            backendContainer.integralCalculator.selectMethod(parsingResult);
            backendContainer.integralCalculator.setAccuracy(parsingResult);
            
            //If the program got here - trigger calculations and return the result.
            Double result = backendContainer.integralCalculator.performCalculation();
            
            //Add the queries to the session
            //backendContainer.sessionData.addQueryToSession(request.getSession().getId(), lastServicedQueryID);
            addDataToDB(request.getSession().getId());
            return PredefinedCommunicates.calcResult() + result.toString();
        }
        catch(IntegralCalculationException ex)
        {
            return ex.getMessage();
        }
    }
    /**
     * Creates new cookie that contains index of last query made by the user.
     * @param queryIndex
     * @return Cookie with information about index of last made query by the user.
     */
    private Cookie assignCookie(Integer queryIndex)
    {
        return new Cookie(LastQueryCookieServlet.lastQueryCookieName, queryIndex.toString());
    }
    
    public QueryAcceptingServlet()
    {
        backendContainer = BackendContainer.getInstance();
        backendContainer.registerCalcResultListener(this);
        try
        {
            queryDBManager = QueryDBManager.getInstance();
        }
        catch(DBManagerIniException ex)
        {
            System.out.println("Could not initialize the database manager! It will be unavaiable for this session. \n" + ex.getMessage());
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
        
        //Add the SessionData object to this session, if not done yet.
        backendContainer.sessionData.ChkIfSessionBound(request.getSession());
        
        try (PrintWriter out = response.getWriter()) {
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Query processing report</title>");            
            out.println("</head>");
            out.println("<body>");
            String answer = readParams(request);
            out.println(answer);
            response.addCookie(assignCookie(lastMadeQueryID));            
            response.addCookie(assignCookie(lastInnerSessionID));
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

    @Override
    public void newCalculationPerformed(CalculationData calculationData, IntegralData integralData) {
        lastQuery = new SingleQuery(integralData,calculationData); //To change body of generated methods, choose Tools | Templates.
    }

}

/*Get the data from client. Add to the string proper command mark and send to command parser.
If command parser returns INCORRECT type command - send it back to client. Otherwise, if
everything's correct - pass values to calculator and return result.*/