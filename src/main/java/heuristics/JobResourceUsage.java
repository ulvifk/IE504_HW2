package heuristics;

import data.Job;

public record JobResourceUsage(Job job, double startTime, double endTime, double tardiness) {

    public JobResourceUsage {
        tardiness = Math.max(0, endTime - job.dueDate());
    }

    public JobResourceUsage(Job job, double startTime, double endTime) {
        this(job, startTime, endTime, 0);


    }
}
