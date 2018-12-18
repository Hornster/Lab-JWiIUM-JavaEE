package pl.polsl.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.model.exceptions.NoQueryFoundException;
import pl.polsl.model.queryHistory.QueryHistory;
import pl.polsl.model.queryHistory.SingleQuery;

import static junit.framework.TestCase.fail;

/**
 * Test for QueryHistory class (Model).
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0
 */
public class QueryHistoryTests {
    private QueryHistory history;
    private SingleQuery singleQuery;
    private IntegralData integralData;
    private CalculationData calcData;
    /**
     * Set the test method.
     */
    @Before
    public void iniTests()
    {
        history = new QueryHistory();
        integralData = new IntegralData(0.0, 1.0);
        calcData = new CalculationData();

        integralData.setIntegralFunc("f(x) = x^2");
        calcData.setCalculationMethod('t');
        calcData.setResult(286.5);
        calcData.setAccuracy(100000);

        singleQuery = new SingleQuery(integralData, calcData);
    }

    /**
     * Test addItem and GetQuery(<no arguments>) methods.
     */
    @Test
    public void testAddItemGetLastQuery()
    {
        history.addItem(singleQuery);
        try {
            SingleQuery addedQ = history.getQuery();
            Assert.assertEquals(addedQ, singleQuery);
        } catch (NoQueryFoundException e) {
            fail("Added query could not be found!");
        }
    }
    /**
     * Test iterator and foreach loop.
     */
    @Test
    public void testForEach()
    {
        int i = 1;
        for(; i <= 10; i++) {
            singleQuery = new SingleQuery(integralData, calcData);
            singleQuery.setAccuracy(i);
            history.addItem(singleQuery);
        }
        i = 1;
        for(SingleQuery query : history)
        {
            Assert.assertEquals(i, query.getAccuracy());
            i++;
        }

    }
    /**
     * Test GetQuery(int index) method.
     */
    @Test
    public void testGetQueryAt()
    {
        int index = 0;
        history.addItem(singleQuery);
        singleQuery.setAccuracy(20947);
        history.addItem(singleQuery);
        singleQuery.setAccuracy(999999);
        history.addItem(singleQuery);

        try{
            SingleQuery retrievedQ = history.getQuery(index);
            index++;
            retrievedQ = history.getQuery(index);
            index++;
            retrievedQ = history.getQuery(index);
            index++;
        } catch (NoQueryFoundException e) {
            fail("FAILED - query under that index should be found! Index: " + index);
        }
        try
        {
            SingleQuery retriecedQ = history.getQuery(index);
            fail("There should be no item at " + index + "index!");
        } catch (NoQueryFoundException e) {
            //Exception of such type should be in fact thrown before "fail"
        }
    }

    /**
     * Test size method.
     */
    @Test
    public void testSize()
    {
        history.addItem(singleQuery);
        history.addItem(singleQuery);
        history.addItem(singleQuery);

        if(history.size() != 3)
            fail("Wrong size of the history!");
    }

}

