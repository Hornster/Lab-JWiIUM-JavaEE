/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.model;

import pl.polsl.controller.calculation.CalcModuleServerAdapter;
import pl.polsl.controller.calculation.CalculationModule;
import pl.polsl.model.queryHistory.QueryManager;
import pl.polsl.server.CommandParser;

/**Stores only one instance of data on the server. As long as the server's up.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0*/
public class BackendContainer {
    /**
     * Instance of this singleton.
     */
    private static BackendContainer instance;
    /**
     * Stores descriptions of the commands and allows for easy creation of an INCORRECT type
     * of command.
     */
    private CommandsDescriptions commandsDescriptions;
    /**
     * Manages all queries made during the server uptime.
     */
    public QueryManager queryManager= new QueryManager();
    /**
     * Checks if received arguments' values are correct.
     */
    public CommandParser commandParser = new CommandParser();
    /**
     * Calculates the integral using provided data.
     */
    public CalcModuleServerAdapter integralCalculator = new CalcModuleServerAdapter(new CalculationModule());
    
    private BackendContainer()
    {
        commandsDescriptions = CommandsDescriptions.getInstance();
        integralCalculator.addListener(queryManager);
        
    }
    /**
     * If not created yet, creates the only instance of this class.
     * @return Reference to the BackendContainer singleton instance.
     */
    public static BackendContainer getInstance()
    {
        if(instance == null)
        {
            instance = new BackendContainer();
        }
        return instance;
    }
}
