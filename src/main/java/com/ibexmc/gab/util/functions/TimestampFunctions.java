package com.ibexmc.gab.util.functions;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimestampFunctions {
    /**
     * Gets the current timestamp
     * @return Current timestamp
     */
    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Checks if a timestamp is in the future
     * @param timestamp Timestamp to check
     * @return If true, timestamp is in the future
     */
    public static boolean inFuture(Timestamp timestamp) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int res = timestamp.compareTo(now);
        return (res > 0);
    }

    /**
     * Checks if a timestamp is after another timestamp
     * @param timestamp Timestamp to check
     * @param afterTimestamp Timestamp to check against
     * @return If true, timestamp is after the other timestamp
     */
    public static boolean after(Timestamp timestamp, Timestamp afterTimestamp) {
        int res = timestamp.compareTo(afterTimestamp);
        return (res > 0);
    }

    /**
     * Adds a number of seconds to a timestamp
     * @param timestamp Timestamp to add to
     * @param seconds Number of seconds to add
     * @return Timestamp with added seconds
     */
    public static Timestamp addSeconds(Timestamp timestamp, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.SECOND, seconds);
        return new Timestamp(cal.getTime().getTime());
    }

    /**
     * Adds a number of minutes to a timestamp
     * @param timestamp Timestamp to add to
     * @param minutes Number of minutes to add
     * @return Timestamp with added seconds
     */
    public static Timestamp addMinutes(Timestamp timestamp, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.MINUTE, minutes);
        return new Timestamp(cal.getTime().getTime());
    }

    /**
     * Adds a number of hours to a timestamp
     * @param timestamp Timestamp to add to
     * @param hours Number of hours to add
     * @return Timestamp with added seconds
     */
    public static Timestamp addHours(Timestamp timestamp, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.HOUR, hours);
        return new Timestamp(cal.getTime().getTime());
    }

    /**
     * Adds a number of days to a timestamp
     * @param timestamp Timestamp to add to
     * @param days Number of days to add
     * @return Timestamp with added seconds
     */
    public static Timestamp addDays(Timestamp timestamp, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.DATE, days);
        return new Timestamp(cal.getTime().getTime());
    }

    /**
     * Gets the number of seconds until now for the timestamp provided.
     * If the number returns a negative number, now is in the past.
     * @param timestamp Timestamp to check
     * @return Number of seconds until now
     */
    public static int secondsUntilNow(Timestamp timestamp) {
        // Returns number of seconds the timestamp is until now
        // If the number returns a negative number, now is
        // in the past
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long milliseconds = now.getTime() - timestamp.getTime() ;
        return (int) milliseconds / 1000;
    }

    /**
     * Gets number of seconds from now for the provided timestamp
     * @param timestamp Timestamp to check
     * @return Number of seconds from now
     */
    public static int secondsFromNow(Timestamp timestamp) {
        return (secondsUntilNow(timestamp) * -1);
    }

    /**
     * Converts a long into a yyyy/MM/dd HH:mm:ss format
     * @param val Value to convert
     * @return Readable date/time
     */
    public static String longToDateTime(long val){
        Date date = new Date(val);
        Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format.format(date);
    }
}
