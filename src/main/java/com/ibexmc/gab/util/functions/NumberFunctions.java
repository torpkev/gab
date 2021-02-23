package com.ibexmc.gab.util.functions;

public class NumberFunctions {
    /**
     * Checks if the provided string is a double
     * @param value String value to check
     * @return If true, is a double
     */
    public static boolean isDouble(String value) {
        try
        {
            Double.parseDouble(value.trim());
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Checks if the provided string is a long
     * @param value String value to check
     * @return If true, is a long
     */
    public static boolean isLong(String value) {
        try
        {
            Long.parseLong(value.trim());
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Converts a string value into a double
     * @param value String value to convert
     * @return Double value from string, 0 if unable to convert
     */
    public static double stringToDouble(String value) {
        double returnVal = 0d;
        try
        {
            returnVal = Double.parseDouble(value.trim());
        }
        catch(NumberFormatException e)
        {
            // Unable to parse
        }
        return returnVal;
    }

    /**
     * Converts a string value into a long
     * @param value String value to convert
     * @return long value from string, 0 if unable to convert
     */
    public static long stringToLong(String value) {
        long returnVal = 0;
        try
        {
            returnVal = Long.parseLong(value.trim());
        }
        catch(NumberFormatException e)
        {
            // Unable to parse
        }
        return returnVal;
    }


}
