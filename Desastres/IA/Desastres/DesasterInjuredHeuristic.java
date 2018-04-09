
package IA.Desastres;

import aima.search.framework.HeuristicFunction;

public class DesasterInjuredHeuristic implements HeuristicFunction  {

    public double getHeuristicValue(Object state) {
        return ((DesastresState) state).getInjuredPriorityHeuristic ();
    }

}
