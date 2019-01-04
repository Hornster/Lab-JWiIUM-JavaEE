package pl.polsl.model.queryHistory;

import pl.polsl.model.CalculationData;
import pl.polsl.model.IntegralData;

/**Single memento that stores data about given query.
 * All fields have default values (nulls, nulls EVERYWHERE).
 * @author Karol Kozuch
 * @version 1.1.2*/
public class SingleQuery {
    /**
     * The separator used when dumping the data via toString.
     */
    private String lineSeparator = "\r\n";
    /**Math function provided for the query.*/
    private String mathFunction = "f(x) = 0";
    /**Argument of the function above.*/
    private Character argument = 'x';
    /**Beginning of the definite integral.*/
    private double rangeBegin = 0;
    /**End of the definite integral.*/
    private double rangeEnd = 0;
    /**Amount of objects used in approximation.*/
    private int accuracy= 0;
    /**Selected approximation method.*/
    private char method = '\0';
    /**Result of the calculation.*/
    private double result = 0;

    /**
     * Checks if this instance is equal (by values, not ref) to passed object.
     * @param secondQ Object to compare with.
     * @return TRUE if objects are equal by value. False otherwise.
     */
    @Override
    public boolean equals(Object secondQ)
    {
        if(secondQ == null) {
            return false;
        }
        if(!(secondQ instanceof SingleQuery)){
            return false;
        }
        SingleQuery secondQuery = (SingleQuery) secondQ;
        if(secondQ == this) {
            return true;
        }

        return (this.accuracy == secondQuery.accuracy &&
                this.result == secondQuery.result &&
                this.method == secondQuery.method &&
                this.mathFunction.equals(secondQuery.mathFunction) &&
                this.argument.equals(secondQuery.argument) &&
                this.rangeBegin == secondQuery.rangeBegin &&
                this.rangeEnd == secondQuery.rangeEnd);
    }
    public SingleQuery(SingleQuery source)
    {
        this.mathFunction = new String(source.mathFunction);
        this.argument = source.argument;
        this.rangeBegin = source.rangeBegin;
        this.rangeEnd = source.rangeEnd;
        this.accuracy = source.accuracy;
        this.method = source.method;
        this.result = source.result;
    }

    public SingleQuery(){}
    public SingleQuery(IntegralData integralData, CalculationData calcData)
    {
        if(integralData != null) {
            mathFunction = new String(integralData.getFunction());
            argument = integralData.getArgument();
            rangeBegin = integralData.getBeginning();
            rangeEnd = integralData.getEnd();
        }

        if(calcData != null) {
            accuracy = calcData.getAccuracy();
            method = calcData.getCalculationMethod();
            result = calcData.getResult();
        }
    }

    /**
     * Gets the math function that is used in approximation.
     * @return Integral describing math function.
     */
    public String getMathFunction() {
        return mathFunction;
    }
    /**Sets the integral function expression.
     * @param mathFunction New function expression.*/
    public void setMathFunction(String mathFunction) {
        this.mathFunction = mathFunction;
    }

    /**
     * Returns the argument.
     * @return Argument of the math function.
     */
    public Character getArgument() {
        return argument;
    }
    /**Sets the argument that is present in the function.
     * @param argument New integral function argument.*/
    public void setArgument(Character argument) {
        this.argument = argument;
    }

    /**
     * Gets the beginning of the range.
     * @return The end of the integral range.
     */
    public double getRangeBegin() {
        return rangeBegin;
    }
    /**Sets the beginning of the calculation range.
     * @param rangeBegin The new beginning of the calculation range.*/
    public void setRangeBegin(double rangeBegin) {
        this.rangeBegin = rangeBegin;
    }

    /**
     * Gets the end of the range.
     * @return The end of the range.
     */
    public double getRangeEnd() {
        return rangeEnd;
    }
    /**Sets the end of the calculation range.
     * @param rangeEnd The new end of the calculation range.*/
    public void setRangeEnd(double rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    /**
     * Gets the accuracy (amount of geometrical shapes used in approximation).
     * @return Accuracy of the approximation.
     */
    public int getAccuracy() {
        return accuracy;
    }
    /**Sets the amount of geometrical shapes that took part in approximation.
     * @param accuracy New amount of geometrical shapes used to calculate value.*/
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * Gets the method used in approximation.
     * @return Single character that represents used approximation method.
     */
    public char getMethod() {
        return method;
    }
    /**Sets the method used for approximation.
     * @param method Used method. t for trapezoidal, s for squares.*/
    public void setMethod(char method) {
        this.method = method;
    }

    /**
     * Gets the result of the approximation.
     * @return Result of approximation.
     */
    public double getResult() {
        return result;
    }
    /**Sets the result of the approximation.
     * @param result The value calculated in the approximation process.*/
    public void setResult(double result) {
        this.result = result;
    }
    /**
     * Sets new line separator.
     * @param newLineSeparator Separator for lines of the query data.
     */
    public void setLineSeparator(String newLineSeparator)
    {
        lineSeparator = newLineSeparator;
    }
    /**Creates easy to read report of integral calculation process.
     * @return Full description of stored integral calculation process.*/
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Query data: ");
        builder.append(lineSeparator);
        builder.append(" Function: ");
        builder.append(mathFunction);
        builder.append(lineSeparator);
        builder.append(" Of argument: ");
        builder.append(argument);
        builder.append(lineSeparator);
        builder.append(" Calculation range: From ");
        builder.append(rangeBegin);
        builder.append(" to ");
        builder.append(rangeEnd);
        builder.append(lineSeparator);
        builder.append(" Used geometrical shapes in approximation (accuracy): ");
        builder.append(accuracy);
        builder.append(lineSeparator);
        builder.append(" Approximation method: ");
        builder.append(method);
        builder.append(lineSeparator);
        builder.append(" Approximation result: ");
        builder.append(result);
        builder.append(lineSeparator);

        return builder.toString();
    }
    
}