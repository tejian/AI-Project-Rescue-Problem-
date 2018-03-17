package IA.Desastres;

import java.util.ArrayList;

/** 
 * Class representing the state of the problem
 */

public class DesastresState
{
    //////////////////////////////////////////////////////////
    // VARIABLES
    /////////////////////////////////////////////////////

    /**
     * Static data structures that all the states will share
     */
    private static int     s_nHeliPerCentre               = 1;              // all centres have same no of helis
    private static Grupos  s_grupos                       = new Grupos (4, 4);
    private static Centros s_centros                      = new Centros (3, s_nHeliPerCentre, 4); 
    private static int     s_helicopterCapacity           = 15;
    private static float   s_timeToSecurePerson           = 1;               // min
    private static float   s_timeToSecureInjuredPerson    = 2;               // min
    private static float   s_timeBetweenLandingAndTakeOff = 10;              // min
    private static float   s_helicopterSpeed              = 100;             // 100 km / hour

    /**
     * datas to represent the state we are in
     * Helicopter contains the list of trips and trips contain list of groups it saved
     * groups contain in which trip it is being saved on
     * memory cost 2 * number of groups + ints stored in helis (aprox no of gruops)
     */
    ArrayList <Helicopter> m_helicopters;
    ArrayList <Group> m_gruops;

    //////////////////////////////////////////////////////////////
    // FUNCTIONS
    //////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////////////////////////////////

    /** 
     * Empty constuctor
     */
    public DesastresState ()
    {
    }

    /** 
     * Copy Constructor
     */
    public DesastresState (DesastresState p_desastre)
    {
        // simply recursively copies helicopters abd groups
    }

    ///////////////////////////////////////////////////////////////
    // DIFFERENT FUNCTIONS TO GENERATE INTIAL STATES
    ////////////////////////////////////////////////////////////
    /**
     * Default inital state generator
     * simply assigns groups to a flight as long as it can carry
     */
    public void generateInitialStateDefault ()
    {
        int nHelis = s_centros.size() * s_centros.get (0).getNHelicopteros();
        int nGrups = s_grupos.size();
        m_helicopters = new ArrayList <Helicopter> (nHelis); 
        m_gruops      = new ArrayList <Group> (nGrups);

        // helicopter belong to centre -- (current index) % nHeliPerCentre
        for (int i = 0; i < nHelis; ++i)
        {
            m_helicopters.add (new Helicopter());
        }

        for (int i = 0; i < nGrups; ++i)
        {
            m_gruops.add (new Group());
        }

        int currentHeliIndex = 0;
        m_helicopters.get (0).getTrips().add (new Trip());
        int currentTripIndex = 0;
        int currentPersonCount = 0;
        for (int i = 0; i < nGrups; ++i)
        {
            if (currentPersonCount + s_grupos.get (i).getNPersonas() > s_helicopterCapacity)
            {
                currentHeliIndex++;
                currentHeliIndex = currentHeliIndex % m_helicopters.size();
                m_helicopters.get (currentHeliIndex).getTrips().add (new Trip());
                currentTripIndex = m_helicopters.get (currentHeliIndex).getTrips().size() - 1;
                currentPersonCount = 0;
            }
            Trip trip = m_helicopters.get (currentHeliIndex).getTrips().get (currentTripIndex);
            trip.add (i);
            m_gruops.get (i).m_heli = currentHeliIndex;
            m_gruops.get (i).m_trip = currentTripIndex;
            currentPersonCount += s_grupos.get (i).getNPersonas();
        }
    }

    /////////////////////////////////////////////////////////////////////////
    // OPERATORS
    /////////////////////////////////////////////////////////////////////////

    /**
     * swaps the helis and the trip between two groups if is satisfies the
     * capacity constrains of each flight (15 person per flight)
     * @return true if the swap was succesful false if not
     */
    public boolean swapGroup (int x, int y)
    {
        Group a = m_gruops.get (x);
        Group b = m_gruops.get (y);

        // if the groups dont belong to the very same trip
        if (!(a.m_heli == b.m_heli) || !(a.m_trip == b.m_trip))
        {
            // check if swapping is possible .i.e. number of people is less that capacity
            CountTripVisitor visitor = new CountTripVisitor (s_grupos);
            m_helicopters.get (a.m_heli).getTrips().get (a.m_trip).accept(visitor);
            int count = visitor.getCount();
            if (count - s_grupos.get (x).getNPersonas() +
                s_grupos.get (y).getNPersonas() > s_helicopterCapacity)
                return false;

            visitor.setCount (0);
            m_helicopters.get (b.m_heli).getTrips().get (b.m_trip).accept (visitor);
            count = visitor.getCount();
            if (count - s_grupos.get (y).getNPersonas() +
                s_grupos.get (x).getNPersonas() > s_helicopterCapacity)
                return false;
        }

        // now swap them
        Trip atrip = m_helicopters.get (a.m_heli).getTrips().get (a.m_trip);
        Trip btrip = m_helicopters.get (b.m_heli).getTrips().get (b.m_trip);

        int ai = atrip.indexOf (x);
        int bi = btrip.indexOf (y);

        atrip.set (ai, y);
        btrip.set (bi, x);

        int t_heli = a.m_heli;
        int t_trip = a.m_trip;

        a.m_heli = b.m_heli;
        a.m_trip = b.m_trip;

        b.m_heli = t_heli;
        b.m_trip = t_trip;

        return true;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Heuristics
    ///////////////////////////////////////////////////////////////////////////

    /**
     * simple heuristic will simply return the total amount of time
     * consumed in saving all groups
     * (wont account for helis flying in parallel)
     */
    public double getTotalTimeHeuristic()
    {
        return 0;
    }

    //////////////////////////////////////////////////////
    //
    /////////////////////////////////////////////////////
    /**
     * prints all groups and centres
     */
    public void testPrintCentrosAndGrupos()
    {
        System.out.println ("printing centres");
        for (Centro c : s_centros)
        {
            System.out.println ("coord x : " + c.getCoordX());
            System.out.println ("coord y : " + c.getCoordY());
            System.out.println ("no Heli : " + c.getNHelicopteros());
            System.out.println ("----------------------");
        }
        System.out.println ("printing groups");
        for (Grupo g : s_grupos)
        {
            System.out.println ("coord x : " + g.getCoordX());
            System.out.println ("coord y : " + g.getCoordY());
            System.out.println ("no personcount : " + g.getNPersonas());
            System.out.println ("priority : " + g.getPrioridad());
            System.out.println ("----------------------");
        }
    }

    /**
     * prints the state were in
     */
    public void testPrintState()
    {
        System.out.println ("Printing HeliCopters");
        for (int i = 0; i < m_helicopters.size(); ++i)
        {
            Helicopter heli = m_helicopters.get (i);
            System.out.println ("mi centro :" + i / s_nHeliPerCentre);
            System.out.println ("Trips :");
            for (Trip trip : heli.getTrips())
            {
                for (Integer j : trip)
                    System.out.println (j);
                System.out.println ("----");
            }
            System.out.println ("------------------");
        }

        System.out.println ("Printing Grupos");
        for (int i = 0; i < m_gruops.size(); ++i)
        {
            System.out.println ("group : " + i);
            System.out.println ("my heli : " + m_gruops.get (i).m_heli);
            System.out.println ("my trip : " + m_gruops.get (i).m_trip);
            System.out.println ("-----");
        }
    }

    public static void main (String [] args)
    {
        DesastresState hehe = new DesastresState ();
        //hehe.testPrintCentrosAndGrupos();
        hehe.generateInitialStateDefault();
        //hehe.testPrintState();
        //hehe.swapGroup (0,1);
        hehe.swapGroup (2,3);
        hehe.testPrintState();
    }

};
