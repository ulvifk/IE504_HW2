package heuristics.neighborhoodSearch.moves;

import data.Job;
import data.Machine;

public record IntraSwapMove(Machine machine, Job job1, Job job2) implements IMove{

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof IntraSwapMove other)) return false;

        var isSameMachine = machine == other.machine;
        var isSameJob1 = job1 == other.job1;
        var isSameJob2 = job2 == other.job2;

        return isSameMachine && isSameJob1 && isSameJob2;
    }
}
