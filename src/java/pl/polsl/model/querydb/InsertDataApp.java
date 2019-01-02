package pl.polsl.model.querydb;

import java.sql.*;
import pl.polsl.model.queryHistory.SingleQuery;

/**Inserts data into database (queries made by the user).
 *
 * @author Karol KozuchGroup 4 Section 8
 * @version 1.0*/
public class InsertDataApp {
    /**
     * Extracts data from the query in proper sequence and creates an update command out of it.
     * @param query Query with data.
     * @return Update command for the database that contains data from provided query.
     */
    private String prepareQueryUpdateCommand(SingleQuery query, int sessionID)
    {
        char space = ' ';
        StringBuilder command = new StringBuilder();
        command.append("INSERT INTO SessionData VALUES (");
        command.append(sessionID);
        command.append(space);
        command.append(query.getMathFunction());
        command.append(space);
        command.append(query.getRangeBegin());
        command.append(space);
        command.append(query.getRangeEnd());
        command.append(space);
        command.append(query.getArgument());
        command.append(space);
        command.append(query.getResult());
        command.append(space);
        command.append(query.getMethod());
        command.append(space);
        command.append(query.getAccuracy());
        command.append(space);
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
            statement.executeUpdate("INSERT INTO SessionData(full_id) VALUES (" + sessionID + ")");
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
