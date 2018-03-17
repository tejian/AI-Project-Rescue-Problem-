package IA.Desastres;

import java.util.ArrayList;


/**
 * Class reprsenting one trip made by a helicopter
 * Contains index of groups it carried during the trip
 */
public class Trip extends ArrayList <Integer>
{
    /**
     * Get the number of people this trip will save
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
}
