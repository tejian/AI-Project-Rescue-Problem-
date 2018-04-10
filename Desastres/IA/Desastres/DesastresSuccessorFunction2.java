package IA.Desastres;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * This Succesor function has the following operations.
 * Swaping a group with another group
 * Moving a group to any trip
 * creating new empty trip and moving a group to it.
 */

public class DesastresSuccessorFunction2 implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();

        HeuristicFunction hf = new DesasterTotalTimeHeuristic();
        // HeuristicFunction hf = new DesasterInjuredHeuristic();

        // cast to desaster state
        DesastresState ds = (DesastresState) aState;
        // get number of groups
        int ngroup = ds.getNGroups();


        // swap against each group if they are not same or its possible
        for (int i = 0; i < ngroup; ++i)
            for (int j = 0; j < ngroup; ++j)
            {
                // pass if same group
                if (i == j)
                    continue;
                DesastresState nds = ds.clone();
                // only add if swapping is possible
                if (nds.swapGroup (i, j))
                {
                    double val = hf.getHeuristicValue (nds);
                    // double val = 0;
                    retVal.add (new Successor ("Swap " + Integer.toString (i) + " " + Integer.toString (j) + " " + Double.toString (val), nds));
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

