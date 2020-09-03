package com.ibexmc.gab.util;

public class NumberFunctions {
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
}
