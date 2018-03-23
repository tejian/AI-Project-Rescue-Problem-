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
            for (int j = 0; j < ngroup; ++j)
            {
                if (i == j)
                    continue;
                DesastresState nds = ds.clone();
                if (nds.swapGroup (i, j))
                {
                    retVal.add (new Successor (Integer.toString (i) + " " + Integer.toString (j), nds));
                }
            }

        return retVal;
    }
}

