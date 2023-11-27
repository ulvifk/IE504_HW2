package heuristics;

import data.Job;
import data.Machine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Solution {
    public final Map<Machine, List<Job>> schedule;
    private final Map<Machine, List<JobResourceUsage>> resourceUsage;
    public double objectiveValue;

    public Solution(Map<Machine, List<Job>> schedule) {
        this.schedule = schedule;
        this.resourceUsage = new HashMap<>();
        this.schedule.keySet().forEach(machine -> this.resourceUsage.put(machine, new LinkedList<>()));

        this.update();
    }

    private double calculateObjective(){
        return this.resourceUsage.values().stream()
                .flatMap(List::stream)
                .mapToDouble(jobResource -> Math.max(0,
                        jobResource.endTime() -
                        jobResource.job().dueDate()) *
                        jobResource.job().tardinessWeight())
                .sum();
    }

    private void calculateResourceUsage(){
        for(var kvp : this.schedule.entrySet()){
            var machine = kvp.getKey();
            var jobs = kvp.getValue();
            var startTime = 0.0;
            for(var job : jobs){
                var jobIndex = jobs.indexOf(job);
                var isSetup = jobIndex == 0 ||
                        !jobs.get(jobIndex - 1).family().equals(job.family());

                if (isSetup) startTime += machine.getSetupTime(job.family());

                var endTime = startTime + job.getProcessingTime(machine);
                this.resourceUsage.get(machine).add(new JobResourceUsage(job, startTime, endTime));
                startTime = endTime;
            }
        }
    }

    public Solution copy(){
        var newSchedule = new HashMap<Machine, List<Job>>();
        for(var kvp : this.schedule.entrySet()){
            var machine = kvp.getKey();
            var jobs = kvp.getValue();
            newSchedule.put(machine, new LinkedList<>(jobs));
        }
        return new Solution(newSchedule);
    }

    public void update(){
        this.calculateResourceUsage();
        this.objectiveValue = this.calculateObjective();
    }
}
