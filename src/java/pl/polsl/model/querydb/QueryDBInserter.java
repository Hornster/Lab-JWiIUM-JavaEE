package pl.polsl.model.querydb;

import java.sql.*;
import pl.polsl.model.queryHistory.SingleQuery;

/**Inserts data into database (queries made by the user).
 *
 * @author Karol KozuchGroup 4 Section 8
 * @version 1.0*/
public class QueryDBInserter {
    /**
     * Prepares the given cjunk of data to be inserted into SQL command. Inserts data of String type.
     * Puts a ' sign at the beginning and end of the string and a space at the very end, in the end.
     * @param builder StringBuilder that is being used to create the command.
     * @param dataChunk Atomic data chunk that shall be added to command.
     */
    private void addDataChunk(StringBuilder builder, String dataChunk)
    {
        char space = ' ';
        String dividor = "'";
        char coma = ',';
        
        builder.append(dividor);
        builder.append(dataChunk);
        builder.append(dividor);
        builder.append(coma);
        builder.append(space);
    }
    /**
     * Prepares the given cjunk of data to be inserted into SQL command. Inserts data of Integer type.
     * Puts a ' sign at the beginning and end of the string and a space at the very end, in the end.
     * @param builder StringBuilder that is being used to create the command.
     * @param dataChunk Atomic data chunk that shall be added to command.
     */
    private void addDataChunk(StringBuilder builder, Integer dataChunk)
    {
        char space = ' ';
        char coma = ',';
        
        builder.append(dataChunk);
        builder.append(coma);
        builder.append(space);
    }
    /**
     * Prepares the given cjunk of data to be inserted into SQL command. Inserts data of Double type.
     * Puts a ' sign at the beginning and end of the string and a space at the very end, in the end.
     * @param builder StringBuilder that is being used to create the command.
     * @param dataChunk Atomic data chunk that shall be added to command.
     */
    private void addDataChunk(StringBuilder builder, Double dataChunk)
    {
        char space = ' ';
        char coma = ',';
        
        builder.append(dataChunk);
        builder.append(coma);
        builder.append(space);
    }
    /**
     * Extracts data from the query in proper sequence and creates an update command out of it.
     * @param query Query with data.
     * @param sessionID ID of the session that made the query.
     * @return Update command for the database that contains data from provided query.
     */
    private String prepareQueryUpdateCommand(SingleQuery query, Integer sessionID)
    {
        StringBuilder command = new StringBuilder();
        command.append("INSERT INTO QueriesData(session_id, "
            + "formula, "
            + "range_beg,"
            + "range_end,"
            + "argument,"
            + "result,"
            + "method,"
            + "accuracy) VALUES (");
        addDataChunk(command, sessionID);
        addDataChunk(command, query.getMathFunction());
        addDataChunk(command, query.getRangeBegin());
        addDataChunk(command, query.getRangeEnd());
        addDataChunk(command, query.getArgument().toString());
        addDataChunk(command, query.getResult());
        addDataChunk(command, query.getMethod().toString());
        addDataChunk(command, query.getAccuracy());
        command.delete(command.length()-2, command.length());               //Get rid of last coma
        command.append(")");
        
        return command.toString();
    }
    /**
     * Inserts sessionID to the database into the SessionData table
     * @param dbConnection Connection with the database.
     * @param sessionID ID of the session that shall be inserted (id applied by the database).
     */
    public void insertData(Connection dbConnection, String sessionID) {
        // make a connection to DB
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate("INSERT INTO SessionsData(full_id) VALUES (" + "'" + sessionID + "'" + ")");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    /**
     * Inserts query data to QueriesData table.Does NOT insert new sessionID if it is non-exoistent - use
     * proper overload of this method.
     * @param dbConnection Connection with the database. 
     * @param sessionID ID of the session that caused data insertion.
     * @param query Data of the query that has been made.
     * 
     */
    public void insertData(Connection dbConnection, int sessionID, SingleQuery query) {
        // make a connection to DB
        try {
            Statement statement = dbConnection.createStatement();
            String command = prepareQueryUpdateCommand(query, sessionID);
            statement.executeUpdate(command);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
}
//INSERT INTO QueriesData(session_id, formula, range_beg,range_end,argument,result,method,accuracy) VALUES ('1', 'z+z+z+z+z+z', '-4.0', '5.0', 'z', '-9000.0', 's', '13423')