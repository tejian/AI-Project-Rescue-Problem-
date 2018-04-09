package IA.Desastres;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;


public class DesastresSuccessorFunction implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();

        // cast to desaster state
        DesastresState ds = (DesastresState) aState;
        // get number of groups
        int ngroup = ds.getNGroups();


        // swap against each group if they are not same or its possible
        for (int i = 0; i < ngroup; ++i)
        {
            for (int j = 0; j < ngroup; ++j)
            {
                // pass if same group
                if (i == j)
                    continue;
                DesastresState nds = ds.clone();
                // only add if swapping is possible
                if (nds.swapGroup (i, j))
                {
                    double val = nds.getTotalTimeHeuristic();
                    // double val = nds.getInjuredPriorityHeuristic();
                    // double val = 0;
                    retVal.add (new Successor ("Swap " + Integer.toString (i) + " " + Integer.toString (j) + " " + Double.toString (val), nds));
                }
            }
        }


        // move a group to every possible trips
        for (int i = 0; i < ds.getHelicopters().size(); i++)
        {
            for (int j = 0; j < ds.getHelicopters().get (i).getTrips().size(); j++) 
            {
                for (int k = 0; k < ngroup; k++)
                {
                    DesastresState nds = ds.clone ();
                    if (nds.moveGroup (k, i, j))
                    {
                        double val = nds.getTotalTimeHeuristic();
                        // double val = nds.getInjuredPriorityHeuristic();
                        // double val = 0;
                        retVal.add (new Successor ("Move " 
                                                   + Integer.toString (k) + " " 
                                                   + Integer.toString (i) + " " 
                                                   + Integer.toString (j) + " " 
                                                   + Double.toString (val), nds));
                    }
                }
            }
        }

        // creation of empty trip
        for (int i = 0; i < ds.getHelicopters().size(); i++) 
        {
            for (int k = 0; k < ngroup; k++) 
            {
                DesastresState nds = ds.clone ();
                // create an empty trip at the end
                nds.getHelicopters().get (i).getTrips ().add (new Trip());
                int j = nds.getHelicopters().get (i).getTrips().size() - 1;
                // move every single group to it
                if (nds.moveGroup (k, i, j))
                {
                        double val = nds.getTotalTimeHeuristic();
                        // double val = nds.getInjuredPriorityHeuristic();
                        // double val = 0;
                        retVal.add (new Successor ("Creating and moving " 
                                                   + Integer.toString (k) + " " 
                                                   + Integer.toString (i) + " " 
                                                   + Double.toString (val), nds));
                }
            }
        }

        // TODO: Need to optimize this part  <02-04-18, Sabin> //

        return retVal;
    }
}

