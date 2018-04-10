
package IA.Desastres;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;
import java.util.List;


/**
 * This succesor function has the following operators
 * swapping any two trips in different helicopters
 * swapping groups in same trip
 * moving a group to any trip
 * creating new empty trip and moving a group to it
 */
public class DesastresSuccessorFunction4 implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();

        HeuristicFunction hf = new DesasterTotalTimeHeuristic();
        // HeuristicFunction hf = new DesasterInjuredHeuristic();

        // cast to desaster state
        DesastresState ds = (DesastresState) aState;
        // get number of groups
        int ngroup = ds.getNGroups();


        // swap only positions of groups in a trip
        for (Helicopter heli : ds.getHelicopters())
            for (Trip t : heli.getTrips())
                for (Integer i : t)
                    for (Integer j : t)
                    {
                        if (i == j)
                            continue;
                        DesastresState nds = ds.clone();
                        if (nds.swapGroup (i, j))
                        {
                            double val = hf.getHeuristicValue (nds);
                            // double val = 0;
                            retVal.add (new Successor ("Swap " + Integer.toString (i) + " " + Integer.toString (j) + " " + Double.toString (val), nds));
                        }
                    }


        // swap trips 
        for (int h1 = 0; h1 < ds.getHelicopters().size(); h1++)
            for (int t1 = 0; t1 < ds.getHelicopters(). get (h1).getTrips().size(); ++t1)
                for (int h2 = 0; h2 < ds.getHelicopters().size(); h2++)
                    for (int t2 = 0; t2 < ds.getHelicopters(). get (h2).getTrips().size(); ++t2)
                    {
                        if (h1 == h2)
                            continue;
                        DesastresState nds = ds.clone();
                        if (nds.swapTrip (h1, t1, h2, t2))
                        {
                            double val = hf.getHeuristicValue (nds);
                            // double val = 0;
                            retVal.add (new Successor ("Swap heli trip " 
                                                       + Integer.toString (h1) + " " + Integer.toString (t1) + " " 
                                                       + Integer.toString (h2) + " " + Integer.toString (t2) + " " 
                                                       + Double.toString (val), nds));
                        }
                    }

        // move a group to every possible trips
        for (int i = 0; i < ds.getHelicopters().size(); i++)
            for (int j = 0; j < ds.getHelicopters().get (i).getTrips().size(); j++) 
                for (int k = 0; k < ngroup; k++)
                {
                    DesastresState nds = ds.clone ();
                    if (nds.moveGroup (k, i, j))
                    {
                        double val = hf.getHeuristicValue (nds);
                        // double val = 0;
                        retVal.add (new Successor ("Move " 
                                                   + Integer.toString (k) + " " 
                                                   + Integer.toString (i) + " " 
                                                   + Integer.toString (j) + " " 
                                                   + Double.toString (val), nds));
                    }
                }

        // creation of empty trip
        for (int i = 0; i < ds.getHelicopters().size(); i++) 
            for (int k = 0; k < ngroup; k++) 
            {
                DesastresState nds = ds.clone ();
                // create an empty trip at the end
                nds.getHelicopters().get (i).getTrips ().add (new Trip());
                int j = nds.getHelicopters().get (i).getTrips().size() - 1;
                // move every single group to it
                if (nds.moveGroup (k, i, j))
                {
                        double val = hf.getHeuristicValue (nds);
                        // double val = 0;
                        retVal.add (new Successor ("Creating and moving " 
                                                   + Integer.toString (k) + " " 
                                                   + Integer.toString (i) + " " 
                                                   + Double.toString (val), nds));
                }
            }

        return retVal;

    }
}

