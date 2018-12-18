package pl.polsl.model;

import pl.polsl.server.CommandParser;

import java.util.HashMap;
import java.util.Map;
/** Stores info about all available commands on the server.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0
 */
public class CommandsDescriptions {
    /**
     * Allows for easy retrieval of requested command's description.
     */
    private Map<CommandParser.commandType, String> descriptions = new HashMap<>();
    /**
     * Instance of the singleton.
     */
    private static CommandsDescriptions instance;
    private CommandsDescriptions()
    {
        descriptions.put(CommandParser.commandType.HELP, "Shows info about available commands.");
        descriptions.put(CommandParser.commandType.CALCULATE, "Tells the server to perform calculations, using data provided to it.");
        descriptions.put(CommandParser.commandType.DISCONNECT, "Disconnects the clientside (you) from the server.");
        descriptions.put(CommandParser.commandType.GET_HISTORY, "Returns all successful queries done while the server is running.");
        descriptions.put(CommandParser.commandType.SET_INTEGRAL, "Sets the integral data. Usage:" +
                "\n\r set_integral <math_function>, <range_begin>, <range_end> \n\r" +
                "Example: set_integral f(x)= x^2, 0, 1.6");
        descriptions.put(CommandParser.commandType.SET_METHOD, "Sets the approximation method data. Usage:" +
                "\n\r set_method <method:=[t; s]>, < shapes_count:>0 >" +
                "\n\r Where: t - trapezoidal method, s - square method, \">0\" much bigger than 0 (bigger is slower, but more accurate) " +
                "\n\r Example: set_method t, 10000");
    }

    /**
     * Gets description of the given command.
     * @param commandType Type of command.
     * @return String containing description of given command.
     */
    public String getCommandsDesc(CommandParser.commandType commandType)
    {
        return descriptions.get(commandType);
    }

    /**
     * Creates a command containing no values, of type INCORRECT and with passed description.
     * @param description String with description that will be put in the command description field.
     * @return Incorrect command(response to incorrect command), ready to be sent back to clientside.
     */
    public ServerCommand createIncorrectCommand(String description)
    {
        ServerCommand command = new ServerCommand(CommandParser.commandType.INCORRECT);
        command.setDescription(description);

        return command;
    }
       /**
        * If instance of this singleton is not present - creates it.
        * @return Instance of this class' singleton.
        */
    public static CommandsDescriptions getInstance()
    {
        if(instance == null)
        {
            instance = new CommandsDescriptions();
        }

        return instance;
    }
}
