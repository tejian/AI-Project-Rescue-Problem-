package IA.Desastres;

import java.util.ArrayList;

/**
 * Helicopter class contains a list of trips it made
 */
public class Helicopter
{
    private ArrayList <Trip> m_trips = new ArrayList<Trip> ();
    /**
     * Empty Constructor
     */
    public Helicopter() 
    {
    }

    /** 
     * Clones this helicopter
     */
    public Helicopter clone()
    {
        Helicopter heli = new Helicopter();
        for (Trip trip : m_trips)
            heli.getTrips().add (trip.clone());
        return heli;
    }

    /**
     * Gets vector of trip this helicopter will be taking
     */
    public ArrayList <Trip> getTrips()
    {
        return m_trips;
    }

    /**
     * Compute the total time take by this helicopter to carry out all the trips.
     */
    public double computeTotalTripTime (int startx, int starty,final ProblemConfig p_config)
    {
        // if the helimakes no trip
        if (m_trips.isEmpty())
            return 0.0;

        double res = 0.0;
        int validTripCount = 0;
        // else compute trip
        for (Trip trip : m_trips)
        {
            if (trip.isEmpty())
                continue;

            ++validTripCount;

            // time from centre to first location
            int x = p_config.grupos.get (trip.get (0)).getCoordX();
            int y = p_config.grupos.get (trip.get (0)).getCoordY();
            double timeToFirstLocation = Util.timeToTravel (startx, starty, x, y, p_config.helicopterSpeed);
            // time from last location to centre
            x = p_config.grupos.get (trip.get (trip.size() - 1)).getCoordX();
            y = p_config.grupos.get (trip.get (trip.size() - 1)).getCoordY();
            double timeFromLastLocation = Util.timeToTravel (startx, starty, x, y, p_config.helicopterSpeed);

            // calcalate time between trips
            double timeOfTrip = trip.computeTripTime (p_config);
            res += timeToFirstLocation + timeFromLastLocation + timeOfTrip;
        }

        // time between flights
        res += (validTripCount - 1) *p_config.timeBetweenLandingAndTakeOff;
        return res;
    }

    // testing
    public static void main (String[] args)
    {
        ProblemConfig s_config = new ProblemConfig ( 1,                   
                                                    new Grupos (4, 4),    
                                                    new Centros (3, 1, 4),
                                                    15,                   
                                                    1,                    
                                                    2,                    
                                                    10,                   
                                                    100                   
                                                    );
        Helicopter h = new Helicopter();
        if (h.computeTotalTripTime (0, 0, s_config) == 0.0)
            System.out.println ("correct answer");
        else
            System.out.println ("wrong answer");

        Trip a = new Trip();
        a.add (0);
        a.add (1);
        h.getTrips().add (a);
        if (h.computeTotalTripTime (0, 0, s_config) == 43.429913261266655)
            System.out.println ("correct answer");
        else
            System.out.println ("wrong answer");

        h.getTrips().add (a);
        if (h.computeTotalTripTime (0, 0, s_config) == 96.85982652253331)
            System.out.println ("correct answer");
        else System.out.println ("wrong answer");
    }
}

