import IA.Desastres.DesastresState;
import IA.Desastres.DesasterTotalTimeHeuristic;
import IA.Desastres.DesasterInjuredHeuristic;
import IA.Desastres.DesastresSuccessorFunction;
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
        // interactiveMode();
        executionMode(Integer.parseInt (args [0]));
    }

    /**
     * Hard code variables of program and simply run from terminal
     * to see results.
     */
    private static void executionMode (int sd)
    {
        // problem settings
        // modify here to test variables
        int groupCount = 100;
        int heliPerCentre = 1;
        int centreCount = 5; 
        int seed = sd;

        DesastresState.getProblemConfig().nHeliPerCentre = heliPerCentre;
        DesastresState.getProblemConfig().grupos = new Grupos (groupCount, seed);
        DesastresState.getProblemConfig().centros = new Centros (centreCount, heliPerCentre, seed);

        DesastresState state = new DesastresState();

        // initial states
        // state.generateInitialStateAllOnOneHeli();
        // state.generateInitialStateOneGroupPerTrip();
        state.generateInitialStateMaxGroupPerTrip();

        // search techniques
        desasterHillClimbinSearch        (state, new DesasterTotalTimeHeuristic());
        // desasterHillClimbinSearch        (state, new DesasterInjuredHeuristic());
        // desasterSimulatedAnnealingSearch (state, new DesasterTotalTimeHeuristic());
        // desasterSimulatedAnnealingSearch (state, new DesasterInjuredHeuristic());
    }

    /**
     * Interactive mode, Choose variables on program
     * during execution.
     */
    private static void interactiveMode()
    {
        Scanner sc = new Scanner (System.in);

        int groupCount;
        int heliPerCentre;
        int centreCount;
        int seed;

        System.out.println("choose no of group ?");
        groupCount = sc.nextInt();
        System.out.println("choose helicopters per centre ?");
        heliPerCentre = sc.nextInt();
        System.out.println("choose no of centres ?");
        centreCount = sc.nextInt();
        System.out.println("choose random Seed ?");
        seed = sc.nextInt();

        DesastresState.getProblemConfig().nHeliPerCentre = heliPerCentre;
        DesastresState.getProblemConfig().grupos = new Grupos (groupCount, seed);
        DesastresState.getProblemConfig().centros = new Centros (centreCount, heliPerCentre, seed);

        DesastresState state = new DesastresState();


        int initialState;
        System.out.println("choose different initial state ?");
        System.out.println("1. only one heli of one centre is used. And only one group per trip");
        System.out.println("2. all heli used. only one group per trip");
        System.out.println("3. all heli used. trip contains maximum group that it can carry");
        initialState = sc.nextInt();
        switch (initialState)
        {
            case 1:
                state.generateInitialStateAllOnOneHeli();
                break;
            case 2:
                state.generateInitialStateOneGroupPerTrip();
                break;
            default:
                state.generateInitialStateMaxGroupPerTrip();
                break;
        }

        int heuristic;
        System.out.println ("which heuristic ?");
        System.out.println ("1. total time");
        System.out.println ("2. priority to injured");
        heuristic = sc.nextInt();
        HeuristicFunction hf;
        switch (heuristic)
        {
            case 2:
                hf = new DesasterInjuredHeuristic();
                break;
            default:
                hf = new DesasterTotalTimeHeuristic();
        }


        int method;
        System.out.println("choose algorithm ?");
        System.out.println("1. hillclimbing");
        System.out.println("2. simulated annealing");
        method = sc.nextInt();
        switch (method)
        {
            case 2:
                desasterSimulatedAnnealingSearch (state, hf);
            default:
                desasterHillClimbinSearch (state, hf);
        }
    }

    /**
     * hill climbing search to solve problem
     */
    private static void desasterHillClimbinSearch (DesastresState p_state, HeuristicFunction hf)
    {
        try
        {
            Problem problem =  new Problem(p_state,
                                           new DesastresSuccessorFunction(),
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
    private static void desasterSimulatedAnnealingSearch (DesastresState p_state, HeuristicFunction hf)
    {
        try
        {
            Problem problem =  new Problem(p_state,
                                           new DesastresSuccessorFunctionSA(),
                                           new DesastresGoalTest(),
                                           hf);
            Search search =  new SimulatedAnnealingSearch (1000, 100, 5, 0.001);
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
