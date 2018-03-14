package IA.Desastres;

import java.util.ArrayList;

/** 
 * Class representing the state of the problem
 */

public class DesastresState
{
    //////////////////////////////////////////////////////////
    // variables
    /////////////////////////////////////////////////////
    /**
     * Static data structures that all the states will share
     */
    private static Grupos  s_grupos;
    private static Centros s_centros;
    private static int     s_helicopterCapacity           = 15;
    private static float   s_timeToSecurePerson           = 1;   // min
    private static float   s_timeToSecureInjuredPerson    = 2;   // min
    private static float   s_timeBetweenLandingAndTakeOff = 10;  // min
    private static float   s_helicopterSpeed              = 100; // 100 km / hour

    //////////////////////////////////////////////////////////
    // functions
    /////////////////////////////////////////////////////

    public static void setGrupos (Grupos p_grupos)
    {
        s_grupos = p_grupos;
    }

    public static void setCentros (Centros p_centros)
    {
        s_centros = p_centros;
    }

    public DesastresState ()
    {
    }

};
