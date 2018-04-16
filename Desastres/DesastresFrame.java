
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.HeuristicFunction;

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

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import java.util.Scanner;

import javax.swing.SpinnerNumberModel;


public class DesastresFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public DesastresFrame() {
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        // new SpinnerNumberModel (, 1, 99999999, 1)
        nGroupSpinner = new javax.swing.JSpinner(new SpinnerNumberModel (100 , 1, 99999999, 1));
        jLabel2 = new javax.swing.JLabel();
        nCentreSpinner = new javax.swing.JSpinner(new SpinnerNumberModel (5, 1, 99999999, 1));
        jLabel4 = new javax.swing.JLabel();
        seedSpinner = new javax.swing.JSpinner(new SpinnerNumberModel (0, 0, 99999999, 1));
        jLabel3 = new javax.swing.JLabel();
        nHeliSpinner = new javax.swing.JSpinner(new SpinnerNumberModel (1, 1, 99999999, 1));
        jLabel6 = new javax.swing.JLabel();
        initialStateComboBox = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        heuristicFunctionComboBox = new javax.swing.JComboBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        successorFunctionComboBox = new javax.swing.JComboBox();
        HCSearchButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        iterationSpinner = new javax.swing.JSpinner(new SpinnerNumberModel (16000, 1, 99999999, 1));
        jLabel8 = new javax.swing.JLabel();
        stepSpinner = new javax.swing.JSpinner(new SpinnerNumberModel (100, 1, 99999999, 1));
        jLabel9 = new javax.swing.JLabel();
        kSpinner = new javax.swing.JSpinner(new SpinnerNumberModel (30, 1, 99999999, 1));
        jLabel10 = new javax.swing.JLabel();
        lambdaInput = new javax.swing.JTextField();
        SASearchButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Desastres");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setToolTipText("");
        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        jPanel2.setLayout(new java.awt.GridLayout(6, 2, 50, 10));

        jLabel1.setText("Number Of Groups");
        jPanel2.add(jLabel1);
        jPanel2.add(nGroupSpinner);

        jLabel2.setText("Number Of Centres");
        jPanel2.add(jLabel2);

        nCentreSpinner.setName(""); // NOI18N
        jPanel2.add(nCentreSpinner);

        jLabel3.setText("Number Of Helicopter Per Centre");
        jPanel2.add(jLabel3);
        jPanel2.add(nHeliSpinner);

        jLabel4.setText("Seed");
        jPanel2.add(jLabel4);
        jPanel2.add(seedSpinner);

        jLabel6.setText("Initial State generator");
        jPanel2.add(jLabel6);

        initialStateComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "one trip for one group and all trips in one helicopter", "one trip for one group", "max number of groups for each trip" }));
        jPanel2.add(initialStateComboBox);

        jLabel11.setText("Heuristic Funtion");
        jPanel2.add(jLabel11);

        heuristicFunctionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "total time ", "total time + time to save injured" }));
        jPanel2.add(heuristicFunctionComboBox);

        jPanel1.add(jPanel2);

        jTabbedPane1.setName("Simulated annealing"); // NOI18N

        jPanel3.setLayout(new java.awt.GridLayout(2, 2, 50, 50));

        jLabel5.setText("Successor Function");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel5);

        successorFunctionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "move group + create new trip", "move group + create new trip + swap group", "move group + create new trip + swap trips", "move group + create new trip + swap trip in different helicopters" }));

        jPanel3.add(successorFunctionComboBox);

        HCSearchButton.setText("Search");
        HCSearchButton.addActionListener ( new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt)
            {
                hillClimbingSearch();
            }
        });
        jPanel3.add(HCSearchButton);

        jTabbedPane1.addTab("Hill Climbing", jPanel3);

        jPanel4.setLayout(new java.awt.GridLayout(2, 1, 50, 50));

        jPanel5.setLayout(new java.awt.GridLayout(2, 8, 20, 20));

        jLabel7.setText("Iteration");
        jPanel5.add(jLabel7);
        jPanel5.add(iterationSpinner);

        jLabel8.setText("max steps");
        jPanel5.add(jLabel8);
        jPanel5.add(stepSpinner);

        jLabel9.setText("k");
        jPanel5.add(jLabel9);
        jPanel5.add(kSpinner);

        jLabel10.setText("lamba");
        jPanel5.add(jLabel10);

        lambdaInput.setText("0.00026");
        jPanel5.add(lambdaInput);

        jPanel4.add(jPanel5);

        SASearchButton.setText("Search");
        SASearchButton.addActionListener ( new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt)
            {
                simulatedAnnealingSearch();
            }
        });
        jPanel4.add(SASearchButton);

        jTabbedPane1.addTab("Simulated Annealing", jPanel4);

        jPanel1.add(jTabbedPane1);
        jTabbedPane1.getAccessibleContext().setAccessibleName("");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jPanel1);
        jPanel1.getAccessibleContext().setAccessibleName("mainPanel");

        pack();
    }

    // ========================================================
    
    /**
     * return choosen succesor function for hill climbing
     */
    private SuccessorFunction getHCSuccessorFunction ()
    {
        String sf = (String) successorFunctionComboBox.getSelectedItem();
        SuccessorFunction sfun = null;
        if      ( sf == "move group + create new trip"                                      ) sfun = new DesastresSuccessorFunction () ;
        else if ( sf == "move group + create new trip + swap group"                         ) sfun = new DesastresSuccessorFunction2 () ;
        else if ( sf == "move group + create new trip + swap trips"                         ) sfun = new DesastresSuccessorFunction3 () ;
        else if ( sf == "move group + create new trip + swap trip in different helicopters" ) sfun = new DesastresSuccessorFunction4 () ;
        return sfun;
    }

    /**
     * return choosen heuristic function
     */
    private HeuristicFunction getHeuristicFunction ()
    {
        String sf = (String) heuristicFunctionComboBox.getSelectedItem();
        HeuristicFunction hf = null;
        if (sf ==  "total time "                           ) hf = new DesasterTotalTimeHeuristic() ;
        else if (sf == "total time + time to save injured" ) hf = new DesasterInjuredHeuristic() ;
        return hf;
    }

    /**
     * return desastres state with the given initial state
     */
    private DesastresState createDesastresState ()
    {
        DesastresState ds = new DesastresState();

        String sf = (String) initialStateComboBox.getSelectedItem();
        if (sf == "one trip for one group and all trips in one helicopter" ) ds.generateInitialStateAllOnOneHeli() ;
        if (sf == "one trip for one group"                                 ) ds.generateInitialStateOneGroupPerTrip() ;
        if (sf == "max number of groups for each trip"                     ) ds.generateInitialStateMaxGroupPerTrip() ;

        return ds;
    }

    /**
     * initialize problem data
     */
    private void setupProblem ()
    {
        int groupCount    = (Integer) nGroupSpinner.getValue();
        int heliPerCentre = (Integer) nHeliSpinner.getValue();
        int centreCount   = (Integer) nCentreSpinner.getValue();
        int seed          = (Integer) seedSpinner.getValue ();

        DesastresState.getProblemConfig().nHeliPerCentre = heliPerCentre;
        DesastresState.getProblemConfig().grupos = new Grupos (groupCount, seed);
        DesastresState.getProblemConfig().centros = new Centros (centreCount, heliPerCentre, seed);
    }

    /**
     * activate simulated annealing search
     */
    private void simulatedAnnealingSearch ()
    {
        setupProblem();

        System.out.println("================================================");
        System.out.println("executing simulated annealing search .........");
        System.out.println("wait for a moment....");


        int iter  = (Integer) iterationSpinner.getValue();
        int step  = (Integer) stepSpinner.getValue();
        int k     = (Integer) kSpinner.getValue();
        float lam = Float.parseFloat (lambdaInput.getText());

        try
        {
            Problem problem =  new Problem(createDesastresState(),
                                           new DesastresSuccessorFunctionSA(),
                                           new DesastresGoalTest(),
                                           getHeuristicFunction());

            Search search =  new SimulatedAnnealingSearch (iter, step, k, lam);
            SearchAgent agent = new SearchAgent(problem,search);


            DesastresState ds = (DesastresState) search.getGoalState();
            System.out.print("heuristic value :");
            System.out.println(getHeuristicFunction().getHeuristicValue (ds));
            printInstrumentation(agent.getInstrumentation());
            System.out.println();
            ds.testPrintState();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("================================================");
    }

    /** 
     * activate hill climbind search
     */
    private void hillClimbingSearch ()
    {
        setupProblem();

        System.out.println("================================================");
        System.out.println("executing hill climbing search .............");
        System.out.println("wait for a moment....");
        try
        {
            Problem problem =  new Problem(createDesastresState(),
                                           getHCSuccessorFunction(),
                                           new DesastresGoalTest(),
                                           getHeuristicFunction());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            DesastresState ds = (DesastresState) search.getGoalState();
            System.out.print("heuristic value :");
            System.out.println(getHeuristicFunction().getHeuristicValue (ds));
            System.out.println();
            ds.testPrintState();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("================================================");
    }

    /** 
     * print nodes traversed
     */
    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {

            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    /**
     * print moves made
     */
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }

    // ====================================================================


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DesastresFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton HCSearchButton;
    private javax.swing.JButton SASearchButton;
    private javax.swing.JComboBox heuristicFunctionComboBox;
    private javax.swing.JComboBox initialStateComboBox;
    private javax.swing.JSpinner iterationSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner kSpinner;
    private javax.swing.JTextField lambdaInput;
    private javax.swing.JSpinner nCentreSpinner;
    private javax.swing.JSpinner nGroupSpinner;
    private javax.swing.JSpinner nHeliSpinner;
    private javax.swing.JSpinner seedSpinner;
    private javax.swing.JSpinner stepSpinner;
    private javax.swing.JComboBox successorFunctionComboBox;
}
