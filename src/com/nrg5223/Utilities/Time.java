package com.nrg5223.Utilities;

/**
 * A container of static methods related to time.
 */
public abstract class Time {

    /**
     * Get a string in the format hh:mm:ss
     *
     * @param timeInSec time in seconds
     * @return a string in the format hh:mm:ss
     */
    public static String string(double timeInSec) {
        int hours = hoursIn(timeInSec);
        int mins = minsIn(timeInSec);
        int secs = secsIn(timeInSec);

        String hourStr = Integer.toString(hours);
        String minStr = Integer.toString(mins);
        String secStr = Integer.toString(secs);
        if (hours < 10)
            hourStr = "0" + hourStr;
        if (mins < 10)
            minStr = "0" + minStr;
        if (secs < 10)
            secStr = "0" + secStr;
        return hourStr + ":" + minStr + ":" + secStr;
    }

    public static int hoursIn(double timeInSec) {
        return (int) timeInSec / 3600;
    }
    public static int minsIn(double timeInSec) {
        return (int) (timeInSec % 3600) / 60 ;
    }
    public static int secsIn(double timeInSec) {
        return (int) timeInSec % 60;
    }
}
