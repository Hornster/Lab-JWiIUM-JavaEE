package pl.polsl.controller.calculation;

import javafx.util.Pair;
import pl.polsl.model.ServerCommand;
import pl.polsl.model.exceptions.IntegralCalculationException;
import pl.polsl.model.queryHistory.CalcResultListener;

public class CalcModuleServerAdapter {
    /**The adaptee of this adapter.*/
    private CalculationModule calculationModule;

    public CalcModuleServerAdapter(CalculationModule calcModuleRef)
    {
        calculationModule = calcModuleRef;
    }

    /**Assigns input function to integral.
     * @param serverCommand Syntax of newly input function.*/
    public void setFunction(ServerCommand serverCommand)
    {
        int formulaIndex = ServerCommand.setIntegralValues.FORMULA.getValue();
        String functionFormula = serverCommand.getData().get(formulaIndex);

        calculationModule.setFunction(functionFormula);
    }

    /**
     * Adds new observer (listener) to the calculator.
     * @param newListener Observing instance.
     */
    public void addListener(CalcResultListener newListener)
    {
        calculationModule.addListener(newListener);
    }
    /**
     * Triggers the calculations and returns the result.
     * @return Result of the approximation (value of the definite integral in given range).
     * @throws IntegralCalculationException Thrown when there was no function assigned, it was corrupt
     * or the integral was not converging.
     */
    public double performCalculation() throws IntegralCalculationException {
        return calculationModule.performCalculation();
    }


    /**Responsible for selecting method of calculating the integral.
     * @param serverCommand Command that contains char code of the method. 't' stands for Trapezoidal, 's' for Square method.
     * @throws IntegralCalculationException when the provided method symbol was not recognized.*/
    public void selectMethod(ServerCommand serverCommand) throws IntegralCalculationException
    {
        int methodValueIndex = ServerCommand.setMethodValues.METHOD.getValue();

        calculationModule.selectMethod(serverCommand.getData().get(methodValueIndex).charAt(0));
    }

    /**
     * Assigns new range to the integral.
     * @param serverCommand Command containing doubles defining new range. First is beginning, second - end.
     */
    public void assignNewIntegralRange(ServerCommand serverCommand)
    {
        int beginIndex = ServerCommand.setIntegralValues.RANGE_BEGIN.getValue();
        int endIndex = ServerCommand.setIntegralValues.RANGE_END.getValue();
        double begin = Double.parseDouble(serverCommand.getData().get(beginIndex));
        double end = Double.parseDouble(serverCommand.getData().get(endIndex));

        Pair<Double, Double> newRange = new Pair<>(begin, end);
        calculationModule.assignNewIntegralRange(newRange);
    }

    /**
     * Assigns new accuracy (amount of geometrical shapes used in approximation).
     * @param serverCommand How many geometrical shapes to use?(Positive number. Bigger is better, but slower).
     */
    public void setAccuracy(ServerCommand serverCommand)
    {
        int accuracyIndex = ServerCommand.setMethodValues.ACCURACY.getValue();
        int accuracy = Integer.parseInt(serverCommand.getData().get(accuracyIndex));

        calculationModule.setAccuracy(accuracy);
    }
}
