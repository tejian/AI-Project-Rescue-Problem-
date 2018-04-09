package IA.Desastres;

import java.util.ArrayList;

/**
 * Visitor that visits helicopter and gathers
 * datas that helps desastresstate in calcuting its heuristic value.
 */

public class HelicopterHeuristicVisitor implements IVisitor
{
    private double totalTripTime = 0.0;
    private double timeToSaveInjured = 0.0;
    private final ProblemConfig s_config;
    private int centrex;
    private int centrey;

    /**
     * constructor
     */
    public HelicopterHeuristicVisitor (ProblemConfig p_config, int x, int y)
    {
        s_config = p_config;
        centrex = x;
        centrey = y;
    }

    /**
     * visit helicopter and gather necessary data.
     */
    public void visit (Helicopter p_helicopter)
    {
        int startx = centrex;
        int starty = centrey;
        ArrayList <Trip> trips = p_helicopter.getTrips();
        // if the helimakes no trip
        if (trips.isEmpty())
            return;


        double res = 0.0;
        int validTripCount = 0;
        int lastTripWithInjured = 0;
        // else compute trip
        for (Trip trip : trips)
        {
            if (trip.isEmpty())
                continue;

            ++validTripCount;

            // time from centre to first location
            int x = s_config.grupos.get (trip.get (0)).getCoordX();
            int y = s_config.grupos.get (trip.get (0)).getCoordY();
            double timeToFirstLocation = Util.timeToTravel (startx, starty, x, y, s_config.helicopterSpeed);
            // time from last location to centre
            x = s_config.grupos.get (trip.get (trip.size() - 1)).getCoordX();
            y = s_config.grupos.get (trip.get (trip.size() - 1)).getCoordY();
            double timeFromLastLocation = Util.timeToTravel (startx, starty, x, y, s_config.helicopterSpeed);


            // calcalate time between trips
            double timeOfTrip = trip.computeTripTime (s_config);
            res += timeToFirstLocation + timeFromLastLocation + timeOfTrip;

            // if the trip it just saved has injured people we update time taken to save injured
            for (Integer i : trip)
            {
                if (s_config.grupos.get (i).getPrioridad() == 1)
                {
                    timeToSaveInjured = res;
                    lastTripWithInjured = validTripCount;
                }
            }
        }


        // time between flights
        res += (validTripCount - 1) *s_config.timeBetweenLandingAndTakeOff;
        timeToSaveInjured += (lastTripWithInjured - 1) * s_config.timeBetweenLandingAndTakeOff;

        totalTripTime = res;
    }

    /**
     * get the total trip time of the helicopter visited
     */
    public double getTotalTripTime() {
        return totalTripTime;
    }

    /**
     * get the time to save injured in the helicopter visited
     */
    public double getTimeToSaveInjured() {
        return timeToSaveInjured;
    }
}
