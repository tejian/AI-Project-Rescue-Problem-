package IA.Desastres;

import java.lang.Math;


/**
 * Helper class to carry simple math funcionts
 */
public class Util
{
    /**
     * Calculates time to travel from one point to another.
     * @param speed speed in km / hour
     * @return time taken in minutes
     */
    public static double timeToTravel (int x, int y, int x1, int y1, float speed)
    {
        return timeToTravel (distance (x,y,x1,y1), speed);
    }

    public static double timeToTravel (double distance, float speed)
    {
        return (double) distance / speed * 60;
    }

    /**
     * Calculate distance between two points.
     */
    public static double distance (int x, int y, int x1, int y1)
    {
        return Math.sqrt ((x-x1) * (x-x1) + (y-y1) * (y-y1));
    }

    /**
     * Test
     */
    public static void main (String[] args)
    {
        System.out.println (Util.distance (0, 0, 1, 1));
        System.out.println (Util.timeToTravel (0, 0, 1, 1,1));
    }
}
