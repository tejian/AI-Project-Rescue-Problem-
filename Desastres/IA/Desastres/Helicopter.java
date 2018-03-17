package IA.Desastres;

import java.util.ArrayList;

/**
 * Helicopter class contains a list of trips it made
 */
public class Helicopter
{
    private ArrayList <Trip> m_trips = new ArrayList<Trip> ();
    public Helicopter() {}

    public ArrayList <Trip> getTrips()
    {
        return m_trips;
    }
}

