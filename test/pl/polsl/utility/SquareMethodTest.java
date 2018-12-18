package pl.polsl.utility;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.controller.calculation.SquareMethod;
import pl.polsl.model.IntegralData;
import pl.polsl.model.exceptions.IntegralCalculationException;

import static junit.framework.TestCase.fail;

/**Contains tests for the {@link SquareMethod} class.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.2*/
public class SquareMethodTest {
    /**An example integralData to test*/
    private IntegralData integralData;
    /**Tested method object.*/
    private SquareMethod squareMethod;
    /**Initializes objects required for tests.*/
    @Before
    public void iniTests()
    {
        integralData = new IntegralData(0, 0.8);
        integralData.setIntegralFunc("f(x)=x^3+x^2");
        squareMethod = new SquareMethod(integralData, 100000);
    }
    /**Tests calculateIntegral method. Will be passed if the returned by the method value
     * fits in given range.*/
    @Test
    public void testCalculateIntegral()
    {
        try {
            double result = squareMethod.calculateIntegral();

            Assert.assertEquals("Calculatin integralData of x^3 + x^2: ", 0.273, result, 0.02);
        }
        catch(IntegralCalculationException ex)
        {
            fail("Test failed: " + ex.getMessage());
        }
    }
}
