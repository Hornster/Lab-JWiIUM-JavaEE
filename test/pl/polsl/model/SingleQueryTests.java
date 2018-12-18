package pl.polsl.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.model.queryHistory.SingleQuery;

import java.util.LinkedList;
import java.util.List;

/**
 * Test for SingleQuery class (Model).
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0
 */
public class SingleQueryTests {
    private List<SingleQuery> queriesCases = new LinkedList<>();
    private SingleQuery comparsionQuery;
    private int fieldsAmount =7;

    /**
     * Set the test method.
     */
    @Before
    public void iniTests()
    {
        IntegralData data = new IntegralData(-1, 1);
        data.setIntegralFunc("f(x) = x^3 + 1/3x^4");

        CalculationData calcData = new CalculationData();
        calcData.setAccuracy(9999);
        calcData.setResult(8888);
        calcData.setCalculationMethod('t');

        comparsionQuery = new SingleQuery(data, calcData);
        //number of fields in SingleQuery class is 7, + 1 for same by value query.
        for(int i = 0; i < fieldsAmount + 1; i++)
        {
            queriesCases.add(new SingleQuery(comparsionQuery));
        }

        queriesCases.get(0).setAccuracy(1);
        queriesCases.get(1).setArgument('f');
        queriesCases.get(2).setMathFunction("f(x) = hehe");
        queriesCases.get(3).setMethod('h');
        queriesCases.get(4).setRangeBegin(9000.1);
        queriesCases.get(5).setRangeEnd(-9000.1);
        queriesCases.get(6).setResult(12345678);
    }

    /**
     * Test if the comparison by value is made correctly.
     */
    @Test
    public void testEquals()
    {
        for(int i = 0; i < fieldsAmount; i++)
        {
            Assert.assertNotEquals(comparsionQuery, queriesCases.get(i));
        }

        Assert.assertEquals(comparsionQuery, queriesCases.get(7));
    }
}

