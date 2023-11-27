package heuristics.neighborhoodSearch.moves;

import data.Job;
import data.Machine;

public record TransferMove(Machine fromMachine, Machine toMachine, Job job, int toIndex) implements IMove {

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof TransferMove)) {
            return false;
        }
        var other = (TransferMove) obj;
        return fromMachine == other.fromMachine &&
                toMachine == other.toMachine &&
                job == other.job;
    }
}
