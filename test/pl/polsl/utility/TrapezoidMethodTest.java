package pl.polsl.utility;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.controller.calculation.TrapezoidMethod;
import pl.polsl.model.IntegralData;
import pl.polsl.model.exceptions.IntegralCalculationException;

import static junit.framework.TestCase.fail;

/**Contains tests for the {@link TrapezoidMethod} class.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.2*/
public class TrapezoidMethodTest {

    /**An example integralData to test*/
    private IntegralData integralData;
    /**Tested trapezoidMethod object.*/
    private TrapezoidMethod trapezoidMethod;
    /**Initializes objects required for tests.*/
    @Before
    public void initializeData()
    {
        integralData = new IntegralData(0, 0.8);
        integralData.setIntegralFunc("f(x)=x^3+x^2");
        trapezoidMethod = new TrapezoidMethod(integralData, 100000);
    }
    /**Tests calculateIntegral trapezoidMethod. Will be passed if the returned by the trapezoidMethod value
     * fits in given range.*/
    @Test
    public void testCalculateIntegral()
    {
        try {
            double result = trapezoidMethod.calculateIntegral();

            Assert.assertEquals("Calculatin integralData of x^3 + x^2: ", 0.273, result, 0.02);
        }
        catch(IntegralCalculationException ex)
        {
            fail("Test failed: " + ex.getMessage());
        }
    }


}
