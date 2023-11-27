package heuristics.neighborhoodSearch;

import heuristics.Neighbor;
import heuristics.Solution;
import heuristics.neighborhoodSearch.moves.IMove;
import heuristics.neighborhoodSearch.moves.InterSwapMove;


public class InterSwapSearch extends BaseNeighborhoodSearch {

    public InterSwapSearch(Solution solution) {
        super(solution);
        SearchNeighbors();
    }

    protected void SearchNeighbors() {
        for (var kvp : super.solution.schedule.entrySet()) {
            var machine1 = kvp.getKey();
            var schedule = kvp.getValue();

            for (var job1 : schedule) {
                for (var machine2 : super.solution.schedule.keySet()) {
                    if (machine1 == machine2) continue;

                    var schedule2 = super.solution.schedule.get(machine2);
                    for (var job2 : schedule2) {
                        var move = new InterSwapMove(machine1, machine2, job1, job2);
                        var newSolution = applyMove(move);

                        this.neighbors.add(new Neighbor(newSolution, move));
                    }
                }
            }
        }
    }

    protected Solution applyMove(IMove _move) {
        var move = (InterSwapMove) _move;
        var newSolution = super.solution.copy();

        var machine1 = move.machine1();
        var machine2 = move.machine2();

        var job1 = move.job1();
        var job2 = move.job2();

        var schedule1 = newSolution.schedule.get(machine1);
        var schedule2 = newSolution.schedule.get(machine2);

        var node1Index = schedule1.indexOf(job1);
        var node2Index = schedule2.indexOf(job2);

        schedule1.remove(node1Index);
        schedule2.remove(node2Index);

        schedule1.add(node1Index, job2);
        schedule2.add(node2Index, job1);

        newSolution.update();
        return newSolution;
    }
}

