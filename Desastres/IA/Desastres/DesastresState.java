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
    static int s_helicopterPerCenter = 1;
    static ProblemConfig s_config = new ProblemConfig       (
     /* nHeliPerCentre,                                   */ s_helicopterPerCenter,
     /* grupos,                                           */ new Grupos (100, 1234),
     /* centros, (centres have same number of helicopter) */ new Centros (5, s_helicopterPerCenter, 1234),
     /* helicopterCapacity,                               */ 15,
     /* timeToSecurePerson, (min)                         */ 1,
     /* timeToSecureInjuredPerson, (min)                  */ 2,
     /* timeBetweenLandingAndTakeOff, (min)               */ 10,
     /* helicopterSpeed  (km/h)                           */ 100,
     /* heliGroupCapacity                                 */ 3
                                                            );



    /**
     * datas to represent the state we are in
     * Helicopter contains the list of trips and trips contain list of groups it saved
     * groups contain in which trip it is being saved on
     * memory cost 2 * number of groups + ints stored in helis (aprox no of gruops)
     */
    ArrayList <Helicopter> m_helicopters = new ArrayList<Helicopter>();
    ArrayList <Group> m_gruops           = new ArrayList<Group>();

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
     * Clones this desaster state.
     */
    public DesastresState clone ()
    {
        DesastresState ds = new DesastresState();
        for (Helicopter heli : m_helicopters)
            ds.getHelicopters().add (heli.clone());
        for (Group group : m_gruops)
            ds.getGroups().add (group.clone());

        return ds;
    }

    ///////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////
    public ArrayList <Helicopter> getHelicopters()
    {
        return m_helicopters;
    }

    public ArrayList <Group> getGroups()
    {
        return m_gruops;
    }

    public int getNGroups()
    {
        return m_gruops.size();
    }

    public static final ProblemConfig getProblemConfig()
    {
        return s_config;
    }


    ///////////////////////////////////////////////////////////////
    // DIFFERENT FUNCTIONS TO GENERATE INTIAL STATES
    ////////////////////////////////////////////////////////////
    /**
     * Default inital state generator.
     * Simply assigns groups to a flight as long as it can carry
     * Must Be Always called at the beginning otherwise
     * all the other functions will result in error
     */
    public void generateInitialStateMaxGroupPerTrip ()
    {
        int nHelis = s_config.centros.size() * s_config.centros.get (0).getNHelicopteros();
        int nGrups = s_config.grupos.size();
        m_helicopters = new ArrayList <Helicopter> (nHelis);
        m_gruops      = new ArrayList <Group> (nGrups);

        // helicopter belong to centre -- (current index) / nHeliPerCentre
        for (int i = 0; i < nHelis; ++i)
            m_helicopters.add (new Helicopter());

        for (int i = 0; i < nGrups; ++i)
            m_gruops.add (new Group());

        int currentHeliIndex = 0;
        m_helicopters.get (0).getTrips().add (new Trip());
        int currentTripIndex = 0;
        int currentPersonCount = 0;
        int currentGroupCount = 0; // number of groups in current trip
        for (int i = 0; i < nGrups; ++i)
        {
            if (currentPersonCount + s_config.grupos.get (i).getNPersonas() > s_config.helicopterCapacity ||
                currentGroupCount >= s_config.heliGroupCapacity)
            {
                currentGroupCount = 0;
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
            currentPersonCount += s_config.grupos.get (i).getNPersonas();
            ++currentGroupCount;
        }
    }

    /**
     * Inital state generator.
     * Simply assigns one groups to one trip
     * Must Be Always called at the beginning otherwise
     * all the other functions will result in error
     */
    public void generateInitialStateOneGroupPerTrip() 
    {
        int nHelis = s_config.centros.size() * s_config.centros.get (0).getNHelicopteros();
        int nGrups = s_config.grupos.size();
        m_helicopters = new ArrayList <Helicopter> (nHelis);
        m_gruops      = new ArrayList <Group> (nGrups);

        // helicopter belong to centre -- (current index) / nHeliPerCentre
        for (int i = 0; i < nHelis; ++i)
            m_helicopters.add (new Helicopter());

        for (int i = 0; i < nGrups; ++i)
            m_gruops.add (new Group());

        int currentHeliIndex = 0;
        for (int i = 0; i < nGrups; ++i)
        {
            Trip trip = new Trip();
            trip.add (i);
            m_helicopters.get (currentHeliIndex).getTrips().add (trip);
            m_gruops.get (i).m_heli = currentHeliIndex;
            m_gruops.get (i).m_trip = m_helicopters.get (currentHeliIndex).getTrips().size() - 1;
            currentHeliIndex++;
            currentHeliIndex = currentHeliIndex % nHelis;
        }
    }

    public void generateInitialStateAllOnOneHeli()
    {
        int nHelis = s_config.centros.size() * s_config.centros.get (0).getNHelicopteros();
        int nGrups = s_config.grupos.size();
        m_helicopters = new ArrayList <Helicopter> (nHelis);
        m_gruops      = new ArrayList <Group> (nGrups);

        // helicopter belong to centre -- (current index) / nHeliPerCentre
        for (int i = 0; i < nHelis; ++i)
            m_helicopters.add (new Helicopter());

        for (int i = 0; i < nGrups; ++i)
            m_gruops.add (new Group());

        int currentHeliIndex = 0;
        for (int i = 0; i < nGrups; ++i)
        {
            Trip trip = new Trip();
            trip.add (i);
            m_helicopters.get (currentHeliIndex).getTrips().add (trip);
            m_gruops.get (i).m_heli = currentHeliIndex;
            m_gruops.get (i).m_trip = m_helicopters.get (currentHeliIndex).getTrips().size() - 1;
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
        // if they are same no need to swap
        if (x == y)
            return false;

        Group a = m_gruops.get (x);
        Group b = m_gruops.get (y);

        // if the groups dont belong to the very same trip
        if (!(a.m_heli == b.m_heli) || !(a.m_trip == b.m_trip))
        {
            // check if swapping is possible .i.e. number of people is less that capacity
            int count = m_helicopters.get (a.m_heli).getTrips().get (a.m_trip).getPersonCount(s_config.grupos);
            if (count - s_config.grupos.get (x).getNPersonas() +
                s_config.grupos.get (y).getNPersonas() > s_config.helicopterCapacity)
                return false;


            count = m_helicopters.get (b.m_heli).getTrips().get (b.m_trip).getPersonCount(s_config.grupos);
            if (count - s_config.grupos.get (y).getNPersonas() +
                s_config.grupos.get (x).getNPersonas() > s_config.helicopterCapacity)
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

    public boolean swapTrip (int helix, int tripx, int heliy, int tripy) 
    {
        Helicopter heli_x = m_helicopters.get (helix);
        Helicopter heli_y = m_helicopters.get (heliy);
        
        Trip trip_x = heli_x.getTrips().get (tripx);
        Trip trip_y = heli_y.getTrips().get (tripy);

        heli_x.getTrips().set (tripx, trip_y);
        heli_y.getTrips().set (tripy, trip_x);

        // update the groups inside trip
        for (Integer i: heli_x.getTrips().get (tripx))
        {
            m_gruops.get (i).m_heli = helix;
            m_gruops.get (i).m_trip = tripx;
        }

        for (Integer i: heli_y.getTrips().get (tripy))
        {
            m_gruops.get (i).m_heli = heliy;
            m_gruops.get (i).m_trip = tripy;
        }
        return true;
    }

    /**
     * Moves group p_g to trip p_t of helicopter p_h if if satisfies
     * capacity constrains of each flight (15 person per flight).
     * pre : given helicopter and trip must exits
     * @return true if the operation was succesful
     */
    public boolean moveGroup (int p_g, int p_h, int p_t)
    {
        Group t_group = m_gruops.get (p_g);

        int curHeliIndex = t_group.m_heli;
        int curTripIndex = t_group.m_trip;

        // if its already in the desired trip do nothing
        if (curHeliIndex == p_h && curTripIndex == p_t)
            return false;

        Trip t_trip = m_helicopters.get (p_h).getTrips (). get (p_t);

        // if not possible return false
        int count = t_trip.getPersonCount (s_config.grupos);
        if (count + s_config.grupos.get (p_g).getNPersonas() > s_config.helicopterCapacity)
            return false;

        // if trip contains 3 groups already then also return false
        if (t_trip.size() >= 3) 
            return false;


        // if possible move 
        // add this group to trip
        t_trip.add (p_g);
        t_group.m_heli = p_h;
        t_group.m_trip = p_t;

        // now remove from current trip
        Trip t_curTrip = m_helicopters.get (curHeliIndex).getTrips().get (curTripIndex);
        t_curTrip.remove (new Integer (p_g));

        // if trip was empty remove trip from heli
        if (t_curTrip.isEmpty())
        {
            m_helicopters.get (curHeliIndex).getTrips(). remove (curTripIndex);
            // if we remove the trip we need to update the remaining trips
            ArrayList<Trip> trips = m_helicopters.get (curHeliIndex).getTrips();
            for (int i = curTripIndex; i < trips.size(); i++)
                for (Integer j : trips.get (i)) 
                    --m_gruops.get (j).m_trip;
        }

        return true;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Heuristics
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Simple heuristic will simply return the total amount of time consumed in saving all groups.
     * (wont account for helis flying in parallel)
     */
    public double getTotalTimeHeuristic()
    {
        double result = 0.0;
        for (int i = 0; i < m_helicopters.size(); ++i)
        {
            int x = s_config.centros.get (i / s_config.nHeliPerCentre).getCoordX();
            int y = s_config.centros.get (i / s_config.nHeliPerCentre).getCoordY();
            Helicopter heli = m_helicopters.get (i);

            HelicopterHeuristicVisitor vis = new HelicopterHeuristicVisitor (s_config, x, y);
            heli.accept (vis);
            result += vis.getTotalTripTime();
        }
        return result;
    }

    /**
     * Heuristic that prioritizes time taken to save the injured people
     */
    public double getInjuredPriorityHeuristic()
    {
        double totalTimeTaken = 0.0;
        double timeToSaveInjured = 0.0;
        for (int i = 0; i < m_helicopters.size(); ++i)
        {
            int x = s_config.centros.get (i / s_config.nHeliPerCentre).getCoordX();
            int y = s_config.centros.get (i / s_config.nHeliPerCentre).getCoordY();
            Helicopter heli = m_helicopters.get (i);

            HelicopterHeuristicVisitor vis = new HelicopterHeuristicVisitor (s_config, x, y);
            heli.accept (vis);
            // update time taken to save injured
            if (vis.getTimeToSaveInjured() > timeToSaveInjured)
                timeToSaveInjured = vis.getTimeToSaveInjured();
            totalTimeTaken += vis.getTotalTripTime();
        }
        return totalTimeTaken + timeToSaveInjured;
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
        for (Centro c : s_config.centros)
        {
            System.out.println ("coord x : " + c.getCoordX());
            System.out.println ("coord y : " + c.getCoordY());
            System.out.println ("no Heli : " + c.getNHelicopteros());
            System.out.println ("----------------------");
        }
        System.out.println ("printing groups");
        for (Grupo g : s_config.grupos)
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
            System.out.println ("mi centro :" + i / s_config.nHeliPerCentre);
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
        hehe.testPrintCentrosAndGrupos();
        // hehe.generateInitialStateOneGroupPerTrip();
        hehe.generateInitialStateAllOnOneHeli();
        hehe.testPrintState();
        //hehe.swapGroup (0,1);
        //hehe.swapGroup (2,3);
        //hehe.testPrintState();
        //System.out.println (hehe.getTotalTimeHeuristic());
    }
};
