package pl.polsl.model.querydb;

import java.sql.*;

/**Ensures that the server launches with created tables. If any of the tables is
 * gone - it will be recreated.
 *
 * @author Karol KozuchGroup 4 Section 8
 * @version 1.0*/
public class QueryDBCreator {
    /**
     * Query used to recreate the SessionsData table.
     */
    private final String sessionDataTableDesc = "CREATE TABLE SessionsData"
            + "(short_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
            + " full_id VARCHAR(50) UNIQUE)";
    /**
     * Query used to recreate the QueriesData table.
     */
    private final String queryDataTableDesc = "CREATE TABLE QueriesData"
            + " (query_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
            + "session_id INTEGER , "
            + "formula VARCHAR(50), "
            + "range_beg FLOAT, "
            + "range_end FLOAT, "
            + "argument CHAR, "
            + "result FLOAT, "
            + "method CHAR, "
            + "accuracy INTEGER, "
            + "CONSTRAINT queryDBCstr FOREIGN KEY (session_id) REFERENCES SessionsData(short_id))";
    
    /**
     * Creates tables, if these are non-existent.
     * @param dbConnection Connection to the database.
     */
    private void createTables(Connection dbConnection) {
          // make a connection to DB
        try  {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(sessionDataTableDesc);
            
            System.out.println("Tables created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        
        try  {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(queryDataTableDesc);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    /**
     * Creates database if necessary, selects it and adds tables if necessary.
     * Keep in mind that this method shall be called only by user that has permissions to create databases and tables!
     * @param dbConnection Connection to the database.
     */
    public void setupDatabase(Connection dbConnection)
    {
        createTables(dbConnection);
    }

    
}
