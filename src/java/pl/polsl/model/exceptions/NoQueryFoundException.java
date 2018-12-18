package pl.polsl.model.exceptions;
/**Exception that is being thrown upon failed try to retrieve
 * stored query data.*/
public class NoQueryFoundException extends Throwable {
    public NoQueryFoundException(String msg)
    {
        super(msg);
    }
}
