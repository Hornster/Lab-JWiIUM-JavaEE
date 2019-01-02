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
     * Reads data from a resultset and puts it in list returned by the method.
     * @param rs ResultSet which the method will be reaing from.
     * @return List containing read queries data. If none were found - list is empty.
     * @throws SQLException when unable to read from resultset.
     */
    private List<SingleQuery> readResultSet(ResultSet rs) throws SQLException
    {
        double rangeBegin, rangeEnd, calcResult; 
        String argument, formula, method;
        int accuracy;
        List<SingleQuery> results = new LinkedList<>();
        
        while(rs.next())
        {
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
}
