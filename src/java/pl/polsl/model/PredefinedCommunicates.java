package pl.polsl.model;
/**Stores predefined communicates that the server can send to the clientside.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0*/
public class PredefinedCommunicates {
    /**
     * Called when there are no queries in the query history.
     * @return Message explaining absence of queries in the history.
     */
    public static  String noQueriesInHistory()
    {
        return "No queries recorded yet, Darling! \\n\r";
    }

    /**
     * Predefined message for answer to disconnect command.
     * @return A farewell message to disconnecting clientside.
     */
    public static String disconnectMessage()
    {
        return "Goodbye! Be well!";
    }

    /**
     * Predefined message informing about correct value assignment.
     * @return Confirmation of value/s assignment.
     */
    public static String valueAssigned()
    {
        return "Passed value\\s were correctly assigned.";
    }

    /**
     * Informs clientside about not recognized approximation method.
     * @return Error message indicating wrong input of approximation method that should be used.
     */
    public static String incorrectCalcMethod()
    {
        return "Provided method was not recognized.";
    }

    /**
     * Simple universal response to correctly parsed and processed command.
     * @return Simple acknowledgment.
     */
    public static String genericAcknowledge()
    {
        return "Acknowledged.\n\r";
    }

    /**
     * Response used when returning computed values.
     * @return Header for returned values.
     */
    public static String calcResult()
    {
        return "Calculations result: ";
    }

    /**
     * Header for HELP command.
     * @return Explanation of the HELP command.
     */
    public static String helpHeader()
    {
        return "Contains clientside-available commands descriptions.";
    }

    /**
     * Header for HELP command description.
     * @return Simple header for the commands description, informing about beginning of the description.
     */
    public static String helpDescriptionHeader()
    {
        return "Available commands: \n\r";
    }

    /**
     * Simple new client greeting message.
     * @return Greeting message.
     */
    public static String getGreetingMessage()
    {
        return "What is it, mortal?";
    }
    /**
     * Message explaining to the client stuff about the server.
     * @return Message that the client will see upon establishing connection with the server.
     */
    public static String getNewConnectionInfo()
    {
        return "If you connected from universalclient, like PuTTy, there's a chance that "
                + "the server answers will be in great disorder. To remedy such situation, "
                + "set in the properties to implicitly add CR to every LF. For PuTTy:\n\r"
                + "Click PPM on name bar after establishing connection. Go to: ChangeSettings -> Terminal and check the \"Implicit CR in every LF\" option. \n\r\n\r"
                + "Also, the first command you enter, be it correct or wrong, will be interpreted "
                + "as incorrect. It's an easter egg. A feature. Completely intended. Next commands will work "
                + "in normal manner, though.";
    }

}
