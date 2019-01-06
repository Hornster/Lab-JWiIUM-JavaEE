package pl.polsl.model;

import pl.polsl.controller.server.CommandParser;

import java.util.LinkedList;
import java.util.List;
/** Stores information about parsed command (status, description, data).
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0
 */
public class ServerCommand {
    /**Defines sequence of arguments in SetIntegral command.*/
    public enum setIntegralValues{

        /**
         *Formula of integral which will be used in calculations.
         */
        FORMULA(0),

        /**
         *Beginning of the range of the calculation.
         */
        RANGE_BEGIN(1),

        /**
         *End of the range of calculation.
         */
        RANGE_END(2);
        public final int value;

        setIntegralValues(int value)
        {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**Defines sequence of arguments in SetMethod command.*/
    public enum setMethodValues{

        /**
         *Method used by approximation.
         */
        METHOD(0),

        /**
         *Accuracy == number of geometrical shapes used in approximation.
         */
        ACCURACY(1);

        /**
         *Integer type value assigned to each enumeration part.
         */
        private final int value;

        setMethodValues(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }
    /**
     * Description associated with this command. Contains info when the stored command is recognized as incorrect.
     */
    private String description;
    /**
     * Type of the command.
     */
    private CommandParser.commandType commandType;
    /**
     * List containing data from the command.
     */
    private List<String> data = new LinkedList<>();

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the list with command data.
     * @return List containing the data associated with command. Every data chunk in form of a string.
     */
    public List<String> getData() {
        return data;
    }


    public CommandParser.commandType getCommandType() {
        return commandType;
    }
    public void setCommandType(CommandParser.commandType newType)
    {
        commandType = newType;
    }

    public ServerCommand(CommandParser.commandType commandType)
    {
        this.commandType = commandType;
    }

    /**
     * Adds new value to command.
     * @param value
     */
    public void addValue(String value)
    {
        data.add(value);
    }

    /**
     * Adds multiple values to command at once.
     * @param values
     */
    public void addValues(List<String> values)
    {
        data = values;
    }

    /**
     * Prepares a description of this object.
     * @return Description and commandType only if commandType was INCORRECT. String formed by concat of command type, description and data List otherwise.
     */
    @Override
    public String toString()
    {
        if(commandType == CommandParser.commandType.INCORRECT)
        {
            return commandType.toString() + ' '+ description;
        }
        else
        {
            String str = commandType.toString() + ' '+ description + "\n\r";

            for(String dataChunk : data)
            {
                str = str.concat(dataChunk + "\n\r");
            }
            return str;
        }
    }
}
