package data;

import java.util.Map;

public record Machine(int id, Map<String, Double> setupTimes) {

    public double getSetupTime(String family) {
        return setupTimes.get(family);
    }

    @Override
    public String toString() {
        return "Machine " + id;
    }
}
