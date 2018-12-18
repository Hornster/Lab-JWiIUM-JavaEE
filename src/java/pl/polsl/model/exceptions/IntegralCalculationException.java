package pl.polsl.model.exceptions;
/**Indicates that a function has been called for calculations but wasn't previously set.*/
public class IntegralCalculationException extends Throwable {
    public IntegralCalculationException(String message)
    {
        super(message);
    }

}
