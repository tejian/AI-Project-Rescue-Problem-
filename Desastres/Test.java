
import IA.Desastres.DesastresState;

public class Test {

    public static void main(String[] args) {
        DesastresState lala = new DesastresState();
        // lala.generateInitialStateOneGroupPerTrip();
        lala.generateInitialStateAllOnOneHeli();
        System.out.println(lala.getTotalTimeHeuristic());

    }
}
