package pl.polsl.controller.calculation;


import pl.polsl.model.IntegralData;
import pl.polsl.model.exceptions.IntegralCalculationException;

/**Interface for integral calculating methods.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0*/
public interface IntegralCalculator {
    /**Sets the precision (in form of amount of geometric shapes used in calculations, for example) of
     * the result.
     * @param precision The desired precision. Shall be positive unless used algorithm states differently.*/
    void setPrecision(int precision);
    /**Sets new integral to be calculated by the algorithm.
     * @param integralData Object containing data of the new integral.*/
    void setIntegralData(IntegralData integralData);

    /**Calculates the integral value.
     * @return Calculated integral value.
     * @throws IntegralCalculationException if the integral was not converging.*/
    double calculateIntegral() throws IntegralCalculationException;
}
