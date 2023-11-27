package heuristics.simulatedAnnealing;

import heuristics.Neighbor;
import heuristics.Solution;
import heuristics.neighborhoodSearch.InterSwapSearch;
import heuristics.neighborhoodSearch.IntraSwapSearch;
import heuristics.neighborhoodSearch.TransferSearch;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private double temperature;
    private double coolingRate;
    private Solution currentSolution;
    public Solution solution;
    private Random random;

    public SimulatedAnnealing(Solution solution, double temperature, double coolingRate, int epochIteration, int seed) {
        this.random = new Random(seed);

        this.currentSolution = solution;
        this.solution = solution;

        this.temperature = temperature;
        this.coolingRate = coolingRate;

        solve(epochIteration);
    }

    private void solve(int epochIteration){
        var epoch = 0;
        while (temperature > 1e-2){
            int iteration = 0;
            while (iteration < epochIteration) {
                var neighbors = getNeighbors(this.currentSolution);

                var randomNeighbor = neighbors.get((int) (this.random.nextDouble() * neighbors.size()));

                if (randomNeighbor.solution().objectiveValue < this.currentSolution.objectiveValue)
                    this.currentSolution = randomNeighbor.solution();

                else {
                    if (this.random.nextDouble() < calculateAcceptanceProbability(randomNeighbor))
                        this.currentSolution = randomNeighbor.solution();
                }

                if (this.currentSolution.objectiveValue < this.solution.objectiveValue){
                    this.solution = this.currentSolution;
                    System.out.println("Epoch: " + epoch + " Temperature: " + temperature + " Cost: " + this.solution.objectiveValue);
                }

                iteration++;
            }
            this.temperature *= this.coolingRate;
            epoch++;
        }
    }

    private double calculateAcceptanceProbability(Neighbor neighbor){
        var delta = neighbor.solution().objectiveValue - this.currentSolution.objectiveValue;

        return Math.exp(-delta / this.temperature);
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
