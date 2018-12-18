package pl.polsl.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.model.exceptions.IntegralCalculationException;

import static junit.framework.TestCase.fail;

/**
 * Test for integralData class (Model).
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0
 */
public class IntegralDataTest {
    private IntegralData integralData = new IntegralData(0.0, 1.0);
    private String testFunc = "f(x) = x^2 + 1/x^3";

    /**
     * Set the test method.
     */
    @Before
    public void iniTests()
    {
        integralData.setIntegralFunc(testFunc);
    }

    /**
     * Are values calculated accordingly to passed function, is an exception thrown when the integral is not converging
     * in calculated spot(result NaN/Infinity)?
     */
    @Test
    public void testCalcValue()
    {
        double x = -5000;

        for(; x < 5000.0; x += 0.5)
        {
            if(x == 0.0) {
                continue;               //We'll check that in another place
            }
            try {
                Assert.assertEquals(x * x + (1 / (x * x * x)), integralData.calcValue(x), 0.005);
            }
            catch(IntegralCalculationException ex)
            {
                fail(ex.getMessage());
            }
        }

        x = 0.0;
        try
        {
            integralData.calcValue(x);
            fail("Integral not converging in " + x + ", should throw IntegralCalculationException!");
        } catch (IntegralCalculationException e) {
            //Exception is expected here.
        }


    }
}
