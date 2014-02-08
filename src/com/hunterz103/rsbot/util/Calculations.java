package com.hunterz103.rsbot.util;

/**
 * Created by Brian on 2/1/14.
 */
public class Calculations {
    public static int toHour(long elapsedTime, int number){
        return (int) (number * 3600000D / elapsedTime);
    }
}
