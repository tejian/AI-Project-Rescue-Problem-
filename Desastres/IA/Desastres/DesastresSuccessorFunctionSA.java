package IA.Desastres;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DesastresSuccessorFunctionSA implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();

        HeuristicFunction hf = new DesasterTotalTimeHeuristic();
        // HeuristicFunction hf = new DesasterInjuredHeuristic();

        // cast to desaster state
        DesastresState ds = (DesastresState) aState;
        // get number of groups
        int ngroup = ds.getNGroups();

        Random rand = new Random();

        int opr = rand.nextInt (3);

        switch (opr) 
        {
            case 0:
            {
                // swapping
                DesastresState nds = ds.clone();

                int i, j;
                do
                {
                    i = rand.nextInt (ngroup);
                    j = rand.nextInt (ngroup);
                } while (!nds.swapGroup (i, j));

                retVal.add (new Successor ("Swap " + Integer.toString (i) + " " + Integer.toString (j), nds));
            }

            case 1:
            {
                // moving
                int i, j, k;
                DesastresState nds = ds.clone();
                do
                {
                    k = rand.nextInt (ngroup);

                    do
                    {
                        i = rand.nextInt (ds.getHelicopters().size());
                    } while (ds.getHelicopters().get (i).getTrips().isEmpty());

                    j = rand.nextInt (ds.getHelicopters().get (i).getTrips().size());
                } while (!nds.moveGroup (k, i, j));

                retVal.add (new Successor ("Move " 
                                           + Integer.toString (k) + " " 
                                           + Integer.toString (i) + " " 
                                           + Integer.toString (j), nds));
            }

            case 3:
            {
                // creating new and moving
                int k = rand.nextInt (ngroup);
                int i = rand.nextInt (ds.getHelicopters().size());

                DesastresState nds = ds.clone();
                nds.getHelicopters().get (i).getTrips ().add (new Trip());
                int j = nds.getHelicopters().get (i).getTrips().size() - 1;
                nds.moveGroup (k, i, j);

                retVal.add (new Successor ("creating and moving " 
                                           + Integer.toString (k) + " " 
                                           + Integer.toString (i) + " " 
                                           + Integer.toString (j), nds));
            }
        }

        System.out.println ("i am here");

        return retVal;
    }
}
