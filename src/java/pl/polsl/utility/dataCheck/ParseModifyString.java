package pl.polsl.utility.dataCheck;

/**Class for parse checks of string to numbers.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0*/
public class ParseModifyString {
    /**Tries to parse the passed string to integer value.
     * @param str The string that will be tested.
     * @return TRUE if it is possible to parse the string to integer, FALSE otherwise.*/
    public static boolean tryStringToInt(String str)
    {
        try {
            Integer.parseInt(str);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }

        return true;
    }
    /**Tries to parse the passed string to double value.
     * @param str The string that will be parsed.
     * @return TRUE if it is possible to parse the string to double, FALSE otherwise.*/
    public static boolean tryStringToDouble(String str)
    {
        try {
            Double.parseDouble(str);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }

        return true;
    }

    /**
     * Removes white characters from passed strings.
     * @param source String to modify.
     * @return White chars free string.
     */
    public static String removeWhiteChars(String source)
    {
        return source.replaceAll("\\s+","");
    }
}
