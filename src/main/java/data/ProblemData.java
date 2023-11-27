package data;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ProblemData {
    public final List<Job> jobs;
    public final List<Machine> machines;

    private Map<String, Double> setupTimesInstance1;
    private Map<String, Double> setupTimesInstance2;

    public ProblemData(String fileLocation) throws IOException {
        this.jobs = new LinkedList<>();
        this.machines = new LinkedList<>();

        setupTimesInstance1 = new HashMap<>();
        setupTimesInstance1.put("A", 0.8);
        setupTimesInstance1.put("B", 1.6);

        setupTimesInstance2 = new HashMap<>();
        setupTimesInstance2.put("A", 2.0);
        setupTimesInstance2.put("B", 1.2);
        setupTimesInstance2.put("C", 0.2);
        setupTimesInstance2.put("D", 0.5);
        setupTimesInstance2.put("E", 1.0);
        setupTimesInstance2.put("F", 1.5);

        readData(fileLocation);
    }

    private void readData(String fileLocation) throws IOException {
        FileReader fileReader = new FileReader(fileLocation);
        CSVReader reader = new CSVReader(fileReader);

        if (fileLocation.contains("instance_1")) {
            this.machines.add(new Machine(1, setupTimesInstance1));
            this.machines.add(new Machine(2, setupTimesInstance1));
        }

        if (fileLocation.contains("instance_2")) {
            this.machines.add(new Machine(1, setupTimesInstance2));
            this.machines.add(new Machine(2, setupTimesInstance2));
        }


        String[] nextLine = reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            String family = nextLine[0];
            int id = Integer.parseInt(nextLine[1]);
            double priorityWeight = Double.parseDouble(nextLine[2]);
            double processTimeMac1 = Double.parseDouble(nextLine[3]);
            double processTimeMac2 = Double.parseDouble(nextLine[4]);
            double dueDate = Double.parseDouble(nextLine[5]);

            Job job = new Job(id, family,
                    Map.of(this.machines.get(0), processTimeMac1, this.machines.get(1), processTimeMac2),
                    dueDate,
                    priorityWeight);

            this.jobs.add(job);
        }
    }
}
