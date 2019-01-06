/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.model.querydb;

import java.sql.Connection;
import java.util.List;
import junit.framework.Assert;
import static junit.framework.TestCase.fail;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.model.CalculationData;
import pl.polsl.model.IntegralData;
import pl.polsl.model.queryHistory.SingleQuery;

/**
 * Test for integralData class (Model).
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0
 */

public class TestDBSelector {
    String sessionID1 = "huehueAleBeka";
    String sessionID2 = "\"I am ze spy. SNORT SNORT\"";
    String sessionID3 = "huehue";
    private SingleQuery query1;
    private SingleQuery query2;
    private SingleQuery query3;
    private int queryID1,queryID2,queryID3;
    private QueryDBManager queryDBManager;
    private Connection dbConnection;
    @Before
    public void iniQueries()
    {   
                //QUERY1
        IntegralData integralData = new IntegralData(8, 9);
        integralData.setIntegralFunc("f(x)=2*x");
        
        CalculationData calcData = new CalculationData();
        calcData.setAccuracy(10000);
        calcData.setCalculationMethod('t');
        calcData.setResult(2.99);
        
        query1 = new SingleQuery(integralData, calcData);
                //QUERY2
        integralData = new IntegralData(12, 13);
        integralData.setIntegralFunc("f(y)=y^3+5*y");
        
        calcData = new CalculationData();
        calcData.setAccuracy(2222);
        calcData.setCalculationMethod('f');
        calcData.setResult(9000);
        
        query2 = new SingleQuery(integralData, calcData);
                //QUERY3
        integralData = new IntegralData(-4, 5);
        integralData.setIntegralFunc("f(z)=z+z+z+z+z+z");
        
        calcData = new CalculationData();
        calcData.setAccuracy(13423);
        calcData.setCalculationMethod('s');
        calcData.setResult(-9000);
        
        query3 = new SingleQuery(integralData, calcData);
    }
    //Check if it is possible to initialize the database connection.
    public void iniManager() throws DBManagerIniException
    {
      queryDBManager = QueryDBManager.getInstance(); 
      dbConnection = queryDBManager.getDBConnection();
    }
    //Check if db can insert a session id.
    private void testInsertSession(String sessionID)
    {
        QueryDBInserter inserter = queryDBManager.getInserter();
        
        inserter.insertData(dbConnection, sessionID);
    }
    private int testRetrieveID(String sessionID)
    {
        QueryDBSelector selector = queryDBManager.getSelector();
        
        return selector.getInternalSessionID(dbConnection, sessionID);
    }
    private int testMakeQuery(int sessionID, SingleQuery query)
    {
        QueryDBInserter inserter = queryDBManager.getInserter();
        QueryDBSelector selector = queryDBManager.getSelector();
        
        inserter.insertData(dbConnection, sessionID, query);
        return selector.getLastQueryID(dbConnection, sessionID);
    }
    private SingleQuery testRetreiveLastQueryByString(String sessionID)
    {
        QueryDBSelector selector = queryDBManager.getSelector();
        
        return selector.readLastQuery(dbConnection, sessionID);
    }
    private SingleQuery testRetreiveLastQueryByQIndex(int sessionID)
    {
        QueryDBSelector selector = queryDBManager.getSelector();
        
        return selector.readLastQueryByLocalSessionID(dbConnection, sessionID);
    }
    private List<SingleQuery> testRetrieveQueries(String sessionID)
    {
        QueryDBSelector selector = queryDBManager.getSelector();
        
        return selector.selectData(dbConnection, sessionID);
    }
    @Test
    public void testDB()
    {
        try
        {
            iniManager();
            testInsertSession(sessionID1);
            testInsertSession(sessionID1);
            testInsertSession(sessionID2);
            
            int localID1 = testRetrieveID(sessionID1);
            Assert.assertEquals(1,localID1);
            int localID2 = testRetrieveID(sessionID2);
            Assert.assertFalse("Error - IDs of different sessions were the same! Unbeleivable!!",localID1 == localID2);
            int localID3 = testRetrieveID("hehe");
            Assert.assertEquals(-1, localID3);
            
            queryID1 = testMakeQuery(localID1, query1);
            queryID3 = testMakeQuery(localID1, query3);
            queryID2 = testMakeQuery(localID2, query2);
                //Test query retreival by index
            SingleQuery retreivedQuery = testRetreiveLastQueryByQIndex(localID1);
            Assert.assertEquals(query3, retreivedQuery);
            retreivedQuery = testRetreiveLastQueryByQIndex(localID2);
            Assert.assertEquals(query2, retreivedQuery);
                //Test query retreival by session full ID
            retreivedQuery = testRetreiveLastQueryByString(sessionID1);
            Assert.assertEquals(query3, retreivedQuery);
            retreivedQuery = testRetreiveLastQueryByString(sessionID2);
            Assert.assertEquals(query2, retreivedQuery);
            retreivedQuery = testRetreiveLastQueryByString(sessionID3);
            Assert.assertFalse("Error - query has been found even if it wasn't supposed to be found!",(query2.equals(retreivedQuery) || query1.equals(retreivedQuery) || query3.equals(retreivedQuery)));
            
            List<SingleQuery> retrievedQueries = testRetrieveQueries(sessionID1);
            Assert.assertTrue("The list of queries is NOT supposed to be emtpy here!", retrievedQueries.size() > 0);
            retrievedQueries = testRetrieveQueries(sessionID2);
            Assert.assertTrue("The list of queries is NOT supposed to be emtpy here!", retrievedQueries.size() > 0);
            retrievedQueries = testRetrieveQueries(sessionID3);
            Assert.assertTrue("The list of queries IS SUPPOSED to be emtpy here!", retrievedQueries.size() <= 0);
            
        }
        catch(DBManagerIniException ex)
        {
            fail(ex.getMessage());
        }
    }
}


