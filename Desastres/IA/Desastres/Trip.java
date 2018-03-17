package IA.Desastres;

import java.util.ArrayList;


/**
 * Class reprsenting one trip made by a helicopter
 * Contains index of groups it carried during the trip
 */
public class Trip extends ArrayList <Integer>
{
    void accept (ITripVisitor p_visitor)
    {
        p_visitor.visit (this);
    }
}
