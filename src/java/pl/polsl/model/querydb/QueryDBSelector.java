package pl.polsl.model.querydb;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import pl.polsl.model.CalculationData;
import pl.polsl.model.IntegralData;
import pl.polsl.model.queryHistory.SingleQuery;
/**Sends queries to the database.
 *
 * @author Karol KozuchGroup 4 Section 8
 * @version 1.0*/
public class QueryDBSelector {
    
    /**
     * Reads single query from passed resultset.
     * @param rs ResultSet to read queries from.
     * @return Query with read data from current row of a ResultSet.
     * @throws SQLException If unable to read data from current ResultSet row.
     */
    private SingleQuery readSingleQuery(ResultSet rs)throws SQLException
    {
        double rangeBegin, rangeEnd, calcResult; 
        String argument, formula, method;
        int accuracy;
        
        rangeBegin = rs.getFloat("range_beg");
        rangeEnd = rs.getFloat("range_end");
        calcResult = rs.getFloat("result");
            
        argument = rs.getString("argument");
        formula = rs.getString("formua");
        method = rs.getString("method");

        accuracy = rs.getInt("accuracy");

        IntegralData integralData = new IntegralData(rangeBegin, rangeEnd);
        integralData.setIntegralFunc(formula);
        CalculationData calcData = new CalculationData();
        calcData.setAccuracy(accuracy);
        calcData.setCalculationMethod(method.charAt(0));
        calcData.setResult(calcResult);

        SingleQuery query = new SingleQuery(integralData, calcData);
        query.setArgument(argument.charAt(0));
        return query;
    }
    /**
     * Reads data from a resultset and puts it in list returned by the method.
     * @param rs ResultSet which the method will be reaing from.
     * @return List containing read queries data. If none were found - list is empty.
     * @throws SQLException when unable to read from resultset.
     */
    private List<SingleQuery> readResultSet(ResultSet rs) throws SQLException
    {
        
        List<SingleQuery> results = new LinkedList<>();
        
        while(rs.next())
        {
            SingleQuery query = readSingleQuery(rs);
            
            results.add(query);
        }
        
        return results;
    }
    /**
     * Retrieves resultset of made queries from the database, accordingly to provided sessionID.
     * @param dbConnection Established connection with the database.
     * @param sessionID ID of the session which queries will be retrieved.
     * @return List of queries made by provided session ID. If none made - list is empty.
     */
    public List<SingleQuery> selectData(Connection dbConnection, String sessionID) {
        List<SingleQuery> results;
        // make a connection to DB
        try{
            Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM SessionsData s, QueriesData q"
                    + "WHERE s.full_id = "+ sessionID +" AND q.session_id = s.short_id");
            
            results = readResultSet(rs);
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            results = new LinkedList<>();
        }
        
        return results;
    }
    /**
     * Reads last query made by user under given sessionID.
     * @param dbConnection Connection to the database.
     * @param sessionID Session of the user.
     * @return Last made query of the user. If none made - returns null.
     */
    public SingleQuery readLastQuery(Connection dbConnection, String sessionID)
    {
        SingleQuery result = null;
        
        try{
            Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM SessionsData s, QueriesData q"
                    + "WHERE s.full_id = "+ sessionID +" AND q.session_id = s.short_id"
                            + "ORDER BY q.session_id ASC");
            if(rs.next())
            {
                result = readSingleQuery(rs);
            }
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            return result;
        }
        
        return result;
    }
    /**
     * Reads last query made by user under given sessionID.
     * @param dbConnection Connection to the database.
     * @return Last made query of the user. If none made - returns null.
     */
    public SingleQuery readLastQuery(Connection dbConnection, int queryID)
    {
        SingleQuery result = null;
        
        try(Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM QueriesData q"
                            + "WHERE q.query_id =" + queryID 
                            + "ORDER BY q.session_id ASC");
                )
        {
            
            if(rs.next())
            {
                result = readSingleQuery(rs);
            }
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            return result;
        }
        
        return result;
    }
    /**
     * Resturns the sessionID in form of an integer that was assigned by the database to given session.
     * If none found - returns -1.
     * @param dbConnection Connection to the database.
     * @param externalSessionID SessionID in form of a string (directly from HttpRequest, for example).
     * @return ID of the session assigned by the database. If none assigned - returns -1.
     */
    public int getInternalSessionID(Connection dbConnection, String externalSessionID)
    {
        int result = -1;
        try{
            Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM SessionsData s"
                    + "WHERE s.full_id = "+ externalSessionID);
            
            if(rs.next())
            {
                result = rs.getInt("short_id");
            }
            
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            result = -1;
        }
        return result;
    }
    /**
     * Searches the ID of last made by this client session query.
     * @param sessionID SessionID of the client.
     * @param dbConnection Connection with the database.
     * @return ID which was given to inserted query by the database engine. -1 if not managed to insert the query.
     */
    public int getLastQueryID(Connection dbConnection, int sessionID)
    {
        int lastQueryID = -1;
        try(Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT q.query_id FROM QueriesData q"
                                                    + "WHERE q.session_id = " + sessionID
                                                    + "ORDER BY q.query_id ASC");
                )
        {
            if(rs.next())
            {
                lastQueryID = rs.getInt("session_id");
            }
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        
        return lastQueryID;
    }
}
