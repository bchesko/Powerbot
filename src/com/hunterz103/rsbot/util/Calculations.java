package com.hunterz103.rsbot.util;

import org.powerbot.script.methods.MethodContext;

/**
 * Created by Brian on 2/1/14.
 */
public class Calculations {
    public static int toHour(long runtime, int number){
        return (int) (number * 3600000D / runtime);
    }


    public static long timeUntilLevel(MethodContext ctx, long runtime, int experienceGained, int skill){
        /*
         * Time Passed * Experience To Next Level
         * --------------------------------------
         *          Experience Gained
         */
        if (experienceGained == 0) return -1;

        int nextLevel = ctx.skills.getLevel(skill) + 1;

        return (runtime * (ctx.skills.getExperienceAt(nextLevel) - ctx.skills.getExperience(skill))) / experienceGained;
    }
}
