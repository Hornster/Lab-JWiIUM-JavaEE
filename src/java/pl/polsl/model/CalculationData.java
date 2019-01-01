package pl.polsl.model;
/**Stores data about method, accuracy (amount of geometric shapes) and
 * other info connected with calculating the value od definite integral.
 *
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0*/
public class CalculationData {
    /**
     * Available types of approximation.
     */
    public enum calcMethodTypes{t, s}
    /**Amount of geometrical shapes used in approximation. Default is 10000*/
    private int accuracy = 10000;
    /**Calculation method that will be used in calculations. Default is 't' (trapezoidal)*/
    private char calculationMethod = 't';
    /**The result of last approximation. Default 0.*/
    private double result = 0.0;

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public char getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(char calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
