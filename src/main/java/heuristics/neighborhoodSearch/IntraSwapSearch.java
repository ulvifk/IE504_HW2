package heuristics.neighborhoodSearch;

import heuristics.Neighbor;
import heuristics.Solution;
import heuristics.neighborhoodSearch.moves.IMove;
import heuristics.neighborhoodSearch.moves.IntraSwapMove;

import java.util.Collections;


public class IntraSwapSearch extends BaseNeighborhoodSearch {

    public IntraSwapSearch(Solution solution) {
        super(solution);
        SearchNeighbors();
    }

    protected void SearchNeighbors() {
        for (var kvp : super.solution.schedule.entrySet()) {
            var machine = kvp.getKey();
            var schedule = kvp.getValue();

            for (var job1 : schedule) {
                for (var job2 : schedule) {
                    if (job1 == job2) continue;

                    var move = new IntraSwapMove(machine, job1, job2);
                    var newSolution = applyMove(move);

                    this.neighbors.add(new Neighbor(newSolution, move));
                }
            }
        }
    }

    protected Solution applyMove(IMove _move) {
        var move = (IntraSwapMove) _move;
        var newSolution = super.solution.copy();

        var machine = move.machine();
        var schedule = newSolution.schedule.get(machine);

        var job1 = move.job1();
        var job2 = move.job2();

        Collections.swap(schedule, schedule.indexOf(job1), schedule.indexOf(job2));
        newSolution.update();
        return newSolution;
    }
}
