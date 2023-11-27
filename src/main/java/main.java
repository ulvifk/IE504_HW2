import data.ProblemData;
import heuristics.constructiveHeuristic.ConstructiveHeuristic;
import heuristics.localSearch.LocalSearch;
import heuristics.simulatedAnnealing.SimulatedAnnealing;

import java.io.IOException;
import java.util.LinkedList;

public class main {

    public static void main(String[] args) throws IOException {
        ProblemData problemData = new ProblemData("HW2_files/instance_2.csv");

        System.out.println("Constructive Heuristic");
        var constructiveHeuristic = new ConstructiveHeuristic(problemData);

        System.out.println();
        System.out.println("Local Search");
        var localSearch = new LocalSearch(constructiveHeuristic.solution, 1000);
        System.out.println("Objective value: " + localSearch.solution.objectiveValue);

        System.out.println();
        System.out.println("Simulated Annealing");
        var simulatedAnnealing = new SimulatedAnnealing(constructiveHeuristic.solution, 5000, 0.95, 2, 0);
    }
}
