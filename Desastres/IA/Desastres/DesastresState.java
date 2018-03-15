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
    private static Grupos  s_grupos = new Grupos (10, 10);
    private static Centros s_centros = new Centros (10, 10, 10); // all centres have same number of helicopter
    private static int nHeliPerCentre = 10;
    private static int     s_helicopterCapacity           = 15;
    private static float   s_timeToSecurePerson           = 1;   // min
    private static float   s_timeToSecureInjuredPerson    = 2;   // min
    private static float   s_timeBetweenLandingAndTakeOff = 10;  // min
    private static float   s_helicopterSpeed              = 100; // 100 km / hour

    //////////////////////////////////
    // SMALL HELPFUL DATA STRUCTURES
    //////////////////////////////////
    private class Trip
    {
        ArrayList <Integer> m_savedGroup;
    }
    private class Helicopter
    {
        ArrayList <Trip> m_trips;
        Helicopter() {}
    };

    private class Group
    {
        int m_heli;
        int m_trip;
        Group() {}
    }

    /**
     * datas to represent the state we are in
     * Helicopter contains the list of trips and trips contain list of groups it saved
     * groups contain in which trip it is being saved on
     * memory cost 2 * number of groups (much nore in reality)
     */
    ArrayList <Helicopter> m_helicopters;
    ArrayList <Group> m_gruops;


    //////////////////////////////////////////////////////////////
    // FUNCTIONS
    //////////////////////////////////////////////////////////////
    /**
     * Number of Centres
     * @return number of centres
     */
    public static int getCentreCount() {
        return s_centros.size();
    }

    /**
     * Number of Groups
     * @return number of gruops
     */
    public static int getGrupoCount()
    {
        return s_grupos.size();
    }

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
    }

    ///////////////////////////////////////////////////////////////
    // DIFFERENT FUNCTIONS TO GENERATE INTIAL STATES
    ////////////////////////////////////////////////////////////
    /**
     * Default inital state generator
     * simply assigns as groups to a flight as long as it can carry
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

        // need to complete
        int currentHeliIndex = 0;
        int currentTripIndex = 0;
        int currentPersonCount = 0;
        for (int i = 0; i < nGrups; ++i)
        {
        }
    }

    /////////////////////////////////////////////////////////////////////////
    // OPERATORS
    /////////////////////////////////////////////////////////////////////////

    /**
     * swaps the helis and the trip between two groups if is satisfies the
     * capacity constrains of each flight (15 person per flight)
     * @pre x and y is less than total number of groups
     */
    public void swapGrup (int x, int y)
    {
    }

    ///////////////////////////////////////////////////////////////////////////
    // Heuristics
    ///////////////////////////////////////////////////////////////////////////

    /**
     */
    public double getTotalTimeHeuristic()
    {
        return 0;
    }

    //////////////////////////////////////////////////////
    //
    /////////////////////////////////////////////////////
    public void testPrint()
    {
        System.out.println (m_helicopters.size());
        System.out.println (m_gruops.size());
    }

    public static void main (String [] args)
    {
        DesastresState hehe = new DesastresState ();
        hehe.generateInitialStateDefault();
        hehe.testPrint();
    }

};
