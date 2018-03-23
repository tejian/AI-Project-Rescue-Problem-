package IA.Desastres;

import aima.search.framework.HeuristicFunction;

public class DesasterTotalTimeHeuristic implements HeuristicFunction  {

    public double getHeuristicValue(Object state) {
        return ((DesastresState) state).getTotalTimeHeuristic();
    }

}
