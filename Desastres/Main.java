import IA.Desastres.DesastresState;
import IA.Desastres.DesasterTotalTimeHeuristic;
import IA.Desastres.DesasterInjuredHeuristic;
import IA.Desastres.DesastresSuccessorFunction;
import IA.Desastres.DesastresSuccessorFunction2;
import IA.Desastres.DesastresSuccessorFunction3;
import IA.Desastres.DesastresSuccessorFunction4;
import IA.Desastres.DesastresSuccessorFunctionSA;
import IA.Desastres.DesastresGoalTest;
import IA.Desastres.Grupos;
import IA.Desastres.Centros;
import IA.Desastres.ProblemConfig; 


import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.HeuristicFunction;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import java.util.Scanner;

class Main
{
    /** 
     * Entry point to demo program.
     */
    public static void main (String[] args)
    {
        if (args.length != 4)
        {
            System.out.println("Usage:   java Main groups helicopter centres seed");
            System.out.println("example: java Main 100 1 5 1234");
            return;
        }
        executionMode(Integer.parseInt (args [0]), Integer.parseInt (args [1]), Integer.parseInt (args [2]), Integer.parseInt (args [3]));
    }

    /**
     * Hard code variables of program and simply run from terminal
     * to see results.
     */
    private static void executionMode (int gc, int hpc, int cc, int sd)
    {
        // problem settings
        // modify here to test variables
        int groupCount = gc;
        int heliPerCentre = hpc;
        int centreCount = cc; 
        int seed = sd;

        // setting problem config
        DesastresState.getProblemConfig().nHeliPerCentre = heliPerCentre;
        DesastresState.getProblemConfig().grupos = new Grupos (groupCount, seed);
        DesastresState.getProblemConfig().centros = new Centros (centreCount, heliPerCentre, seed);

        DesastresState state = new DesastresState();

        // successor function
        SuccessorFunction sc;
        // sc = new DesastresSuccessorFunction();    // move group, create new trip
        // sc = new DesastresSuccessorFunction2();   // move group, create new trip, swap all group
        // sc = new DesastresSuccessorFunction3();   // move group, create new trip, swap group in same trip, swap trip
        sc = new DesastresSuccessorFunction4();   // move group, create new trip, swap group in same trip, swap trip in different helicopters

        // initial states
        // state.generateInitialStateAllOnOneHeli();
        // state.generateInitialStateOneGroupPerTrip();
        state.generateInitialStateMaxGroupPerTrip();


        // heuristic
        HeuristicFunction hf;
        hf = new DesasterTotalTimeHeuristic();           // heuristic 1
        // hf = new DesasterInjuredHeuristic();              // heuristic 2 

        // search techniques
        desasterHillClimbinSearch        (state, sc, hf);
        //
        // desasterSimulatedAnnealingSearch          iter        k     lamda
        // desasterSimulatedAnnealingSearch (state, hf, 26000, 100, 30, (float)0.00026);
        // puedes modificar esas variables de simulated annealing
    }

    /**
     * hill climbing search to solve problem
     */
    private static void desasterHillClimbinSearch (DesastresState p_state, SuccessorFunction sc, HeuristicFunction hf)
    {
        try
        {
            Problem problem =  new Problem(p_state,
                                           sc,
                                           new DesastresGoalTest(),
                                           hf);
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

    /**
     * simulated annealing climbing search to solve problem
     */
    private static void desasterSimulatedAnnealingSearch (DesastresState p_state, HeuristicFunction hf, int steps, int a, int b, float c)
    {
        try
        {
            Problem problem =  new Problem(p_state,
                                           new DesastresSuccessorFunctionSA(),
                                           new DesastresGoalTest(),
                                           hf);
            Search search =  new SimulatedAnnealingSearch (steps, a, b, c);
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            // printActions(agent.getActions());
            for (Object o : agent.getActions())
            {
                System.out.println(hf.getHeuristicValue (o));
            }


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
