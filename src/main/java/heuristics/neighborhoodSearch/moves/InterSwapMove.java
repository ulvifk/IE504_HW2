package heuristics.neighborhoodSearch.moves;

import data.Job;
import data.Machine;

public record InterSwapMove(Machine machine1, Machine machine2, Job job1, Job job2) implements IMove {
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof InterSwapMove)) {
            return false;
        }
        var other = (InterSwapMove) obj;
        return machine1 == other.machine1 &&
                machine2 == other.machine2 &&
                job1 == other.job1 &&
                job2 == other.job2;
    }
}
