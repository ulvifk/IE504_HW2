package heuristics.localSearch;

import data.Job;
import data.Machine;
import heuristics.Neighbor;
import heuristics.Solution;
import heuristics.neighborhoodSearch.InterSwapSearch;
import heuristics.neighborhoodSearch.IntraSwapSearch;
import heuristics.neighborhoodSearch.TransferSearch;

import java.util.*;

public class LocalSearch {
    public Solution solution;

    public LocalSearch(Solution solution, int maxIteration) {
        this.solution = solution;

        solve(maxIteration);
    }

    private void solve(int maxIteration){
        int iteration = 0;
        while (iteration < maxIteration) {
            var neighbors = getNeighbors(this.solution);

            if (neighbors.isEmpty()) {
                System.out.println("No improving neighbor is found");
                break;
            }

            var bestNeighbor = neighbors.stream().min(Comparator.comparing(neighbor -> neighbor.solution().objectiveValue)).orElse(null);

            if (bestNeighbor.solution().objectiveValue < this.solution.objectiveValue) {
                this.solution = bestNeighbor.solution();
                System.out.println("Iteration: " + iteration + " Cost: " + this.solution.objectiveValue);
            }
            else {
                System.out.println("No improving neighbor is found");
                break;
            }

            iteration++;
        }
    }

    private List<Neighbor> getNeighbors(Solution solution){
        var interSwapSearch = new InterSwapSearch(solution);
        var intraSwapSearch = new IntraSwapSearch(solution);
        var transferSearch = new TransferSearch(solution);

        var neighbors = new LinkedList<Neighbor>();
        neighbors.addAll(interSwapSearch.neighbors);
        neighbors.addAll(intraSwapSearch.neighbors);
        neighbors.addAll(transferSearch.neighbors);

        return neighbors;
    }
}
