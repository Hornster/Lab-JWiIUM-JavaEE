package pl.polsl.controller.server;


import java.util.LinkedList;
import java.util.List;
import pl.polsl.controller.server.CommandParser;

/** Used to determine if received commands are correct.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0.1
 */
public class CommandWrapper {
    /**
     * Arguments and values for given command are stored here, in FIFO manner.
     */
    List<String> commandArgs = new LinkedList<>();
    /**
     * Creates a command that's compatible with server-side command structure.
     * @param type Type of the command. Used to recognize by the server parser what command is it really and how many arguments it needs.
     * @return A complete command in form of a string, ready to be sent.
     */
    public String createCommand(CommandParser.commandType type)
    {
        StringBuilder commandBuilder = new StringBuilder();

        commandBuilder.append(type.toString());

        for (String arg : commandArgs) {
            if(!arg.isEmpty())
            {
                commandBuilder.append(' ');
                commandBuilder.append(arg);
                commandBuilder.append(',');
            }
        }

        return commandBuilder.toString();
    }

    /**
     * Checks if provided answer from the server states that data sent by client was correct.
     * @param serverAnswer String containing answer from the server.
     * @return TRUE if the command send by the client was acknowledged. FALSE if the command was incorrect.
     */
    public boolean ChkIfAnswerCorrect(String serverAnswer)
    {
        serverAnswer = serverAnswer.toLowerCase();
        return !serverAnswer.startsWith(CommandParser.commandType.INCORRECT.toString().toLowerCase());
    }

    /**
     * Clears the arguments list.
     */
    public void newArgList()
    {
        commandArgs.clear();
    }

    /**
     * Adds argument to the list, in FIFO manner.
     * @param arg New argument for the command.
     */
    public void addArgument(String arg)
    {
        commandArgs.add(arg);
    }
}

