package com.atypon.simulator;

import java.util.List;
import java.util.PriorityQueue;

public class SchedulerImpl implements Scheduler {

    @Override
    public void assignTasksToProcessors(PriorityQueue<Task> tasksQueue, List<Processor> processors, int clockTime) {
        for (Processor processor : processors) {
            if (processor.isIdle() && !tasksQueue.isEmpty()) {
                processor.setTask(tasksQueue.poll());
                processor.setIdle(false);
                processor.getTask().setEndTask(clockTime + processor.getTask().getExecutionTime() - 1);
            }
        }
    }
}
