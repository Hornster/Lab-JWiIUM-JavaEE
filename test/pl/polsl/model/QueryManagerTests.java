/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.model;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.model.exceptions.NoQueryFoundException;
import pl.polsl.model.queryHistory.QueryHistory;
import pl.polsl.model.queryHistory.QueryManager;
import pl.polsl.model.queryHistory.SingleQuery;
/**
 * Test for QueryManager class (Model).
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0
 */
public class QueryManagerTests {
    private QueryManager manager = new QueryManager();
    private SingleQuery q;
    @Before
    public void iniTests()
    {
        CalculationData calcData = new CalculationData();
        calcData.setAccuracy(1000);
        calcData.setCalculationMethod('t');
        calcData.setResult(67676);
        IntegralData integralData = new IntegralData(0, 1);
        integralData.setIntegralFunc("f(x)=x");
        q = new SingleQuery( integralData, calcData);
    }
    @Test
    public void testGetSelectedQueriesDesc()
    {
        q.setAccuracy(1000);
        manager.addQuery(new SingleQuery(q));
        q.setAccuracy(2000);
        manager.addQuery(new SingleQuery(q));
        q.setAccuracy(3000);
        manager.addQuery(new SingleQuery(q));
        q.setAccuracy(4000);
        manager.addQuery(new SingleQuery(q));
        
        List<Integer> indexes = new LinkedList();
        indexes.add(0);
        indexes.add(1);
        indexes.add(3);
        
        String result = manager.getSelectedQueriesDesc(indexes, "<br>");
        
        Assert.assertFalse(result.contains("3000"));
        Assert.assertTrue(result.contains("1000"));
        Assert.assertTrue(result.contains("2000"));
        Assert.assertTrue(result.contains("4000"));
    }
    @Test
    public void testGetAllQueriesDesc()
    {
        q.setAccuracy(1000);
        manager.addQuery(new SingleQuery(q));
        q.setAccuracy(2000);
        manager.addQuery(new SingleQuery(q));
        q.setAccuracy(3000);
        manager.addQuery(new SingleQuery(q));
        q.setAccuracy(4000);
        manager.addQuery(new SingleQuery(q));
        
        String result = manager.getAllQueriesDesc( "<br>");
        
        Assert.assertTrue(result.contains("3000"));
        Assert.assertTrue(result.contains("1000"));
        Assert.assertTrue(result.contains("2000"));
        Assert.assertTrue(result.contains("4000"));
    }
}
