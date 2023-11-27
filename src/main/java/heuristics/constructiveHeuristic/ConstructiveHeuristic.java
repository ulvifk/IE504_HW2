package heuristics.constructiveHeuristic;

import data.Job;
import data.Machine;
import data.Pair;
import data.ProblemData;
import heuristics.Solution;

import java.util.*;

public class ConstructiveHeuristic {
    private final ProblemData problemData;
    private final Map<Machine, List<Job>> schedule;
    private final Map<Machine, Double> availableTimeOnMachine;
    private final List<Job> unscheduledJobs;
    public final Solution solution;

    public ConstructiveHeuristic(ProblemData problemData) {
        this.problemData = problemData;

        this.schedule = new HashMap<>();
        this.availableTimeOnMachine = new HashMap<>();

        this.problemData.machines.forEach(machine -> {
            this.schedule.put(machine, new LinkedList<>());
            this.availableTimeOnMachine.put(machine, 0.0);
        });

        this.unscheduledJobs = new LinkedList<>(this.problemData.jobs);

        solve();
        this.solution = new Solution(this.schedule);

        System.out.println("Objective value: " + this.solution.objectiveValue);
    }

    private void solve() {
        while (!this.unscheduledJobs.isEmpty()) {
            var nextMachine = this.availableTimeOnMachine.entrySet().stream()
                    .min(Comparator.comparingDouble(Map.Entry::getValue))
                    .get().getKey();
            var nextJob = getNextJob(nextMachine);

            var machineSchedule = this.schedule.get(nextMachine);

            var isSetup = machineSchedule.isEmpty() ||
                    !machineSchedule.get(machineSchedule.size() - 1).family().equals(nextJob.family());
            var timeShift = nextJob.getProcessingTime(nextMachine) +
                    (isSetup ? nextMachine.getSetupTime(nextJob.family()) : 0);

            this.unscheduledJobs.remove(nextJob);
            this.availableTimeOnMachine.replace(nextMachine,
                    this.availableTimeOnMachine.get(nextMachine) + timeShift);

            this.schedule.get(nextMachine).add(nextJob);
        }
    }

    private List<Job> getSortedJobList(Machine machine) {
        return this.unscheduledJobs.stream()
                .sorted(Comparator.comparing(job -> getPriorityScore((Job) job, machine)).reversed())
                .toList();
    }

    private double getPriorityScore(Job job, Machine machine) {
        var machineSchedule = this.schedule.get(machine);

        var isSetup = !machineSchedule.isEmpty() &&
                !machineSchedule.get(machineSchedule.size() - 1).family().equals(job.family());
        var setupTime = 0.0;
        if (isSetup) setupTime = machine.getSetupTime(job.family());

        var slack = (job.dueDate() - this.availableTimeOnMachine.get(machine)
                - job.getProcessingTime(machine)
                - this.availableTimeOnMachine.get(machine)
                - setupTime);

        var slackPenalty = -slack;

        return job.tardinessWeight() * (slackPenalty);
    }

    private Job getNextJob(Machine machine) {
        return this.getSortedJobList(machine).get(0);
    }

}
