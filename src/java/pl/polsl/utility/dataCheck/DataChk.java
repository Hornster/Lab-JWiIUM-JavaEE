package pl.polsl.utility.dataCheck;

import org.mariuszgromada.math.mxparser.Function;

/**Class responsible for checking if data is correct.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0*/
public class DataChk {
    /**Validates the syntax of passed function.
     * @param functionSyntax Function to validate.
     * @return True if syntax is valid. False otherwise.*/
    public static boolean validateFunctionSyntax(String functionSyntax)
    {
        Function mathFunc = new Function (functionSyntax);

        return mathFunc.checkSyntax();
    }

}
