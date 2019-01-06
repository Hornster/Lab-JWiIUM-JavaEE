package pl.polsl.model;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import pl.polsl.model.exceptions.IntegralCalculationException;

/**Container for data about an integral that shall be calculated.
 * Please keep in mind that the function shall be initialized before calling calcValue()
 * and that it shall be changed whether is it correct.
 * Stuff to initialize:
 * -range (constructor)
 * -argument (by default - 'x')
 * -integral function.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.3.2*/
public class IntegralData {
    /**
     * Specifies how much of values this class has to get in order to function properly.
     * Used by CommandParser (Server-side).
     */
    public static final int neededValues = 3;
    /**The expression used in calculations.*/
    private Expression exp1;
    /**The argument of the integral.*/
    private Character argument = 'x';
    /**The function used for calculations.*/
    private Function integralFunc = null;
    /**The beginning x value of range.*/
    private double begin;
    /**The end x value of range.*/
    private double end;
    /**Checks if beginning is smaller than end. If so, swaps the two.
     * Called upon any call to getter of one of the two.*/
    private void TrySwapBeginWithEnd()
    {
        if(begin > end)
        {
            double temp = begin;
            begin = end;
            end = temp;
        }
    }

    /**Calculates the value of integral in position equal to x. For now, the integral is sewn into the code.
     * @throws IntegralCalculationException - when the integral function was not set before calculations.
     * @param argumentValue The x for which the integral value shall be calculated.
     * @return The value of the integral in argumentValue.*/
    public double calcValue(double argumentValue) throws IntegralCalculationException
    {
        if(integralFunc == null)
            throw new IntegralCalculationException("Call for a function that was not initialized first!");

        //Get first argument and set its value.
        exp1.getArgument(0).setArgumentValue(argumentValue);
        Double result = exp1.calculate();

        if(result.isInfinite() || result.isNaN())
        {
            throw new IntegralCalculationException("Provided parameter cannot be calculated: " + argumentValue +
                    ". Value equals " + result + " . " + "Possibly the integral does not converge in " + argumentValue+ '.');
        }
        else
        {
            return result;
        }
    }
    /**A definite integral has to have a range.
     * @param begin The beginning of the range.
     * @param end The end of the range.*/
    public IntegralData(double begin, double end)
    {
        if(begin < end) {
            this.begin = end;
            this.end = begin;
        }
        else
        {
            this.begin = begin;
            this.end = end;
        }
    }

    /**Sets the integral function to calculate.
     * @param newFunction New integral to calculate.*/
    public void setIntegralFunc(String newFunction)
    {
            //integralFunc = new Function("f(" + argument + ")=" + newFunction);
        integralFunc = new Function(newFunction);
        argument = integralFunc.getArgument(0).getArgumentName().charAt(0);
            //exp1 = new Expression("f(" + argument + ")",integralFunc);
        exp1 = new Expression(integralFunc.getFunctionName() + "(" + argument + ")",integralFunc);
        exp1.addArguments(new Argument(argument.toString(), 0.0));
    }
    
    /**Getter for the beginning of the integral range.
     * @return The beginning of the integral range.*/
    public  double getBeginning()
    {
        TrySwapBeginWithEnd();
        return this.begin;
    }
    /**Getter for the end of the integral range.
     * @return The end of the integral range.*/
    public double getEnd()
    {
        TrySwapBeginWithEnd();
        return this.end;
    }
    /**Sets the beginning of the calculation range for the integral.
     * @param newValue New value of the beginning of the range.*/
    public void setBeginning(double newValue)
    {
        begin = newValue;
    }
    /**Sets the end of the calculation range for the integral.
     * @param newValue New value of the end of the range.*/
    public void setEnd(double newValue)
    {
        end = newValue;
    }
    /**Gets the argument of the integral, usable only around the package.
     * @return Argument used in the integral.*/
    public char getArgument()
    {
        return argument;
    }
    /**Gets the ,ath function that describes the integral.
     * @return Mathematical function in form of a String.*/
    public String getFunction()
    {
        return integralFunc.getFunctionExpressionString();
    }


}
