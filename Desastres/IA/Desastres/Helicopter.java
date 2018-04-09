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
     * accept visitors.
     */
    public void accept (IVisitor p_visitor) 
    {
        p_visitor.visit (this);
    }
}

