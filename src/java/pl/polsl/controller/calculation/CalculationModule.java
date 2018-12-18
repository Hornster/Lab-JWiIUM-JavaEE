package pl.polsl.controller.calculation;

import javafx.util.Pair;
import pl.polsl.model.CalculationData;
import pl.polsl.model.IntegralData;
import pl.polsl.model.exceptions.IntegralCalculationException;
import pl.polsl.model.queryHistory.CalcResultListener;

import java.util.LinkedList;
import java.util.List;

/** Manages data containers required for calculations and triggers the process itself.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0.2
 */
public class CalculationModule {
    /**List of listeners that observe this module.*/
    private List<CalcResultListener> observers = new LinkedList<>();
    /**The last decision (about used integral calc. method) made by the user*/
    private char lastMethodDecision = '\0';
    /**The integral to calculate.*/
    private IntegralData integral = new IntegralData(0.0, 1.0); //Default range
    /**Container for other data, like used method, result of approximation.*/
    private CalculationData calculationData = new CalculationData();
    /**Field for the calculation method.*/
    private IntegralCalculator calculator;

    /**
     * Passes to all CalcResultListeners information about recently finished approximation.
     */
    private void informListeners()
    {
        for(CalcResultListener listener: observers)
        {
            listener.newCalculationPerformed(calculationData, integral);
        }
    }
    public CalculationModule()
    {
        calculator = new TrapezoidMethod(integral);
        lastMethodDecision = 't';
    }
    /**Assigns input function to integral.
     * @param functionSyntax Syntax of newly input function.*/
    public void setFunction(String functionSyntax)
    {
        integral.setIntegralFunc(functionSyntax);
    }

    /**
     * Adds new observer (listener) to the calculator.
     * @param newListener Observing instance.
     */
    public void addListener(CalcResultListener newListener)
    {
        observers.add(newListener);
    }
    /**
     * Triggers the calculations and returns the result.
     * @return Result of the approximation (value of the definite integral in given range).
     * @throws IntegralCalculationException Thrown when there was no function assigned, it was corrupt
     * or the integral was not converging.
     */
    public double performCalculation() throws IntegralCalculationException {
        calculator.setIntegralData(integral);

        double result = calculator.calculateIntegral();
        calculationData.setResult(result);

        informListeners();

        return result;
    }


    /**Responsible for selecting method of calculating the integral.
     * @param methodCode Char code of the method. 't' stands for Trapezoidal, 's' for Square method.
     * @throws IntegralCalculationException when the provided method symbol was not recognized.*/
    public void selectMethod(char methodCode) throws IntegralCalculationException
    {

        if(lastMethodDecision == methodCode)
            return; //No need to change algorithm since user selected the previously chosen.
        switch (methodCode)
        {
            case 't':
                calculator = new TrapezoidMethod(integral);
                lastMethodDecision = methodCode;
                calculationData.setCalculationMethod(methodCode);
                return;
            case 's':
                calculator = new SquareMethod(integral);
                lastMethodDecision = methodCode;
                calculationData.setCalculationMethod(methodCode);
                return;
            default:
                throw new IntegralCalculationException("Unknown approximation method! Type 't' for trapezoidal or 's' for square.");
        }
    }

    /**
     * Assigns new range to the integral.
     * @param newRange Pair of doubles defining new range. First is beginning, second - end.
     */
    public void assignNewIntegralRange(Pair<Double, Double> newRange)
    {
        integral.setBeginning(newRange.getKey());
        integral.setEnd(newRange.getValue());
    }

    /**
     * Assigns new accuracy (amount of geometrical shapes used in approximation).
     * @param newAccuracy How many geometrical shapes to use?(Positive number. Bigger is better, but slower).
     */
    public void setAccuracy(int newAccuracy)
    {
        calculationData.setAccuracy(newAccuracy);
        calculator.setPrecision(newAccuracy);
    }
}
