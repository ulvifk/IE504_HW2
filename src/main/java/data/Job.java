package data;

import java.util.Map;

public record Job(int id, String family, Map<Machine, Double> processingTimes, double dueDate, double tardinessWeight) {

    public double getProcessingTime(Machine machine) {
        return processingTimes.get(machine);
    }

    @Override
    public String toString() {
        return "Job " + id + " | " + family;
    }
}
