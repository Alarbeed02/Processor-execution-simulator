package com.atypon.simulator;

import java.util.List;
import java.util.PriorityQueue;

public interface Scheduler {

    void assignTasksToProcessors(PriorityQueue<Task> tasksQueue, List<Processor> processors, int clockTime);
}