package pl.polsl.controller.calculation;

import pl.polsl.model.IntegralData;
import pl.polsl.model.exceptions.IntegralCalculationException;

/**Calculates value of an integral in given range using squares method.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.3*/
public class SquareMethod implements IntegralCalculator {
    /**The amount of squares used to approximate the value.*/
    private int precisionLevel;
    /**Currently used integral.*/
    private IntegralData usedIntegralData;

    SquareMethod(IntegralData function){ this(function, 100);}
    public SquareMethod(IntegralData function, int precisionLevel)
    {
        usedIntegralData = function;
        this.precisionLevel = precisionLevel;
    }
    /**Sets new precision (amount of squares used for calculation).
     * Higher is better, but slower.*/
    @Override
    public void setPrecision(int precision) {
        precisionLevel = precision;
    }
    /**Sets the integral data.
     * @param integralData New integral data.*/
    @Override
    public void setIntegralData(IntegralData integralData) {
        usedIntegralData = integralData;
    }
    /**Calculates single rectangle's area.
     * @param currentBeginning X position of the rectangle on the axis.
     * @param interval The width along X axis of the rectangle.
     * @return Area of the rectangle used in approximation.
     * @throws IntegralCalculationException if the integral was not converging.*/
    private double calcSingleRectangle(double currentBeginning, double interval) throws IntegralCalculationException
    {
        double result;
        result = usedIntegralData.calcValue(currentBeginning);
        result *= interval;

        return result;
    }
    /**Calculates the stored integral.
     * @return Value of the integral in specified earlier range.*/
    @Override
    public double calculateIntegral() throws IntegralCalculationException {
        double overallResult = 0.0;
        double currentPos = usedIntegralData.getBeginning();
        double interval = calcInterval();

        while(currentPos <= usedIntegralData.getEnd())
        {
            overallResult+=calcSingleRectangle(currentPos, interval);
            currentPos += interval;
        }

        return overallResult;
    }
    /**Calculates the width of a single rectangle basing on the @precisionLevel and @usedIntegralData beginning and end values.
     * @return Returns the width of a single rectangle (on X axis).*/
    private double calcInterval() {
        return (Math.abs(usedIntegralData.getEnd())+ Math.abs(usedIntegralData.getBeginning()))/precisionLevel;

    }
}
