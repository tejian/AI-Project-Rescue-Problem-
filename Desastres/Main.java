import IA.Desastres.DesastresState;
import IA.Desastres.DesasterTotalTimeHeuristic;
import IA.Desastres.DesastresSuccessorFunction;
import IA.Desastres.DesastresGoalTest;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

class Main
{
    public static void main (String[] args)
    {

        DesastresState state = new DesastresState();

        // commenta i descommenta para probar diferente estado inicial
        // state.generateInitialStateMaxGroupPerTrip();
        state.generateInitialStateOneGroupPerTrip();
        // state.generateInitialStateAllOnOneHeli();


        desasterHillClimbinSearch (state);
    }

    private static void desasterHillClimbinSearch (DesastresState p_state)
    {
        try
        {
            Problem problem =  new Problem(p_state,
                                           new DesastresSuccessorFunction(),
                                           new DesastresGoalTest(),
                                           new DesasterTotalTimeHeuristic());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
} 

