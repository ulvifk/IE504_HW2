package heuristics.neighborhoodSearch;

import heuristics.Neighbor;
import heuristics.Solution;
import heuristics.neighborhoodSearch.moves.IMove;
import heuristics.neighborhoodSearch.moves.TransferMove;


public class TransferSearch extends BaseNeighborhoodSearch {

    public TransferSearch(Solution solution) {
        super(solution);
        SearchNeighbors();
    }

    protected void SearchNeighbors() {
        for (var kvp : super.solution.schedule.entrySet()) {
            var machine1 = kvp.getKey();
            var schedule1 = kvp.getValue();
            for (var job : schedule1){
                for (var kvp2 : super.solution.schedule.entrySet()) {
                    var machine2 = kvp2.getKey();
                    var schedule = kvp2.getValue();
                    if (machine1 == machine2) continue;

                    for (int i = 0; i < schedule.size() + 1; i++) {
                        var move = new TransferMove(machine1, machine2, job, i);
                        var newSolution = applyMove(move);

                        this.neighbors.add(new Neighbor(newSolution, move));
                    }
                }
            }
        }
    }

    protected Solution applyMove(IMove _move) {
        var move = (TransferMove) _move;
        var newSolution = super.solution.copy();

        var fromMachine = move.fromMachine();
        var toMachine = move.toMachine();
        var job = move.job();
        var toIndex = move.toIndex();

        newSolution.schedule.get(fromMachine).remove(job);
        newSolution.schedule.get(toMachine).add(toIndex, job);

        newSolution.update();
        return newSolution;
    }
}
