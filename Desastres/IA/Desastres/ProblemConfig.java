package IA.Desastres;

/**
 * Problem variables.
 * Only one instance of this class is allowed
 * contains global info about the problem such as heli speed, group etc
 */

public class ProblemConfig
{
    public int     nHeliPerCentre;
    public Grupos  grupos;
    public Centros centros;
    public int     helicopterCapacity;
    public float   timeToSecurePerson;
    public float   timeToSecureInjuredPerson;
    public float   timeBetweenLandingAndTakeOff;
    public float   helicopterSpeed;
    public float   heliGroupCapacity;

    /**
     * constructor
     */
    public ProblemConfig (int     p_nHeliPerCentre,
                          Grupos  p_grupos,
                          Centros p_centros,
                          int     p_helicopterCapacity,
                          float   p_timeToSecurePerson,
                          float   p_timeToSecureInjuredPerson,
                          float   p_timeBetweenLandingAndTakeOff,
                          float   p_helicopterSpeed,
                          float   p_heliGroupCapacity)
    {
        nHeliPerCentre               = p_nHeliPerCentre;
        grupos                       = p_grupos;
        centros                      = p_centros;
        helicopterCapacity           = p_helicopterCapacity;
        timeToSecurePerson           = p_timeToSecurePerson;
        timeToSecureInjuredPerson    = p_timeToSecureInjuredPerson;
        timeBetweenLandingAndTakeOff = p_timeBetweenLandingAndTakeOff;
        helicopterSpeed              = p_helicopterSpeed;
        heliGroupCapacity            = p_heliGroupCapacity;
    }
}
