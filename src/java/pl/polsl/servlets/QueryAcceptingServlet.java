/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.model.BackendContainer;
import pl.polsl.model.PredefinedCommunicates;
import pl.polsl.model.ServerCommand;
import pl.polsl.model.exceptions.IntegralCalculationException;
import pl.polsl.server.CommandParser;
import pl.polsl.server.CommandWrapper;

/**
 *
 * @author Karol
 */
public class QueryAcceptingServlet extends HttpServlet {
    
    /**
     * Reference to the backendContainer - singleton that stores the essential classes of the server's functionality.
     */
    private BackendContainer backendContainer;
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
            return PredefinedCommunicates.calcResult() + result.toString();
        }
        catch(IntegralCalculationException ex)
        {
            return ex.getMessage();
        }
    }
    
    public QueryAcceptingServlet()
    {
        backendContainer = BackendContainer.getInstance();
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
            out.println("<title>Query processing report</title>");            
            out.println("</head>");
            out.println("<body>");
            String answer = readParams(request);
            out.println(answer);
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

/*Get the data from client. Add to the string proper command mark and send to command parser.
If command parser returns INCORRECT type command - send it back to client. Otherwise, if
everything's correct - pass values to calculator and return result.*/