package IA.Desastres;

import java.util.ArrayList;


/**
 * Class reprsenting one trip made by a helicopter
 * Contains index of groups it carried during the trip
 */
public class Trip extends ArrayList <Integer>
{
    /**
     * Return sum of elements
     */
    public int getSum ()
    {
        int sum = 0;
        for (Integer i : this)
            sum += i;
        return sum;
    }
}
