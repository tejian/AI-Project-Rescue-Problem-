package IA.Desastres;

import java.util.ArrayList;

/**
 * Class reprsenting one trip made by a helicopter.
 * Contains index of groups it carries during the trip
 */
public class Trip extends ArrayList <Integer>
{
    /**
     * Get the number of people this trip will save.
     * @return sum of number of people of all group that will be saved in this trip
     */
    public int getPersonCount (final Grupos p_grupos)
    {
        int count = 0;
        for (Integer i : this)
        {
            count += p_grupos.get (i).getNPersonas();
        }
        return count;
    }

    /**
     * Clones this instance and returns the new instance.
     */
    public Trip clone ()
    {
        Trip res = new Trip();
        for (Integer i : this)
            res.add (i);
        return res;
    }

    /**
     * Get time taken by trip (from first group location to last).
     * includes time taken to load group and travel betwen groups
     */
    public double computeTripTime (final ProblemConfig p_config)
    {
        // if empty trip
        if (this.isEmpty())
            return 0.0;

        // save first group
        double result = getTimeToLoadGroup (this.get (0), p_config);

        // time to save remaing group + time to travel between groups
        for (int i = 1; i < this.size(); ++i)
        {
            int x =  p_config.grupos.get (this.get (i - 1)).getCoordX();
            int y =  p_config.grupos.get (this.get (i - 1)).getCoordY();
            int x1 = p_config.grupos.get (this.get (i)).getCoordX();
            int y1 = p_config.grupos.get (this.get (i)).getCoordY();
            result +=  Util.timeToTravel (x, y, x1, y1, p_config.helicopterSpeed);
            result += getTimeToLoadGroup (this.get (i), p_config);
        }
        return result;
    }

    /**
     * Time taken to load a group based on priority.
     */
    public double getTimeToLoadGroup (int i, ProblemConfig p_config)
    {
        Grupo g = p_config.grupos.get (i);
        double res;
        // if injured
        if (g.getPrioridad() == 1)
            res = g.getNPersonas() * p_config.timeToSecureInjuredPerson;
        // if not injured
        else
            res = g.getNPersonas() * p_config.timeToSecurePerson;
        return res;
    }

    // tests
    public static void main (String[] args)
    {
        ProblemConfig s_config = new ProblemConfig ( 1,
                                                    new Grupos (4, 4),
                                                    new Centros (3, 1, 4),
                                                    15,
                                                    1,
                                                    2,
                                                    10,
                                                    100,
                                                    3
                                                    );
        Trip a = new Trip();
        a.add (0);
        a.add (1);
        System.out.println (a.computeTripTime (s_config));
        if (a.computeTripTime (s_config) == 25.4985711369071804)
            System.out.println ("correct answer");
        else
            System.out.println ("wrong answer");
    }
}
