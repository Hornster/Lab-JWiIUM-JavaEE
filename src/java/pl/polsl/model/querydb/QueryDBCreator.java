package pl.polsl.model.querydb;

import java.sql.*;

/**Ensures that the server launches with created tables. If any of the tables is
 * gone - it will be recreated.
 *
 * @author Karol KozuchGroup 4 Section 8
 * @version 1.0*/
public class QueryDBCreator {
    
    private String sessionDataTableDesc = "CREATE TABLE SessionsData"
            + "(short_id INTEGER NOT NULL IDENTITY(1,1) PRIMARY KEY UNIQUE, full_id VARCHAR(50) UNIQUE)";
    private String queryDataTableDesc = "CREATE TABLE QueriesData"
            + "(session_id INTEGER , "
            + "formula VARCHAR(50), "
            + "range_beg FLOAT"
            + "range_end FLOAT"
            + "argument CHAR"
            + "result FLOAT"
            + "method CHAR"
            + "accuracy INTEGER"
            + "FOREIGN KEY (session_id) REFERENCES (SessionsData.short_id)";
    public void createTables(Connection dbConnection) {
          // make a connection to DB
        try  {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(sessionDataTableDesc);
            statement.executeUpdate(queryDataTableDesc);
            
            System.out.println("Tables created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
}
