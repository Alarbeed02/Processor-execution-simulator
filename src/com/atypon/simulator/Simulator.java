package com.atypon.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Simulator {
    private final int numProcessor;
    private final int numClockCycle;
    private final String filePath;
    private final List<Processor> processors;
    private final PriorityQueue<Task> queue;
    private final Scheduler scheduler;
    private final List<Clock> clockCycles;

    private int numOfTasks;

    public Simulator(int numProcessor, int numClockCycle, String filePath) {
        this.numProcessor = numProcessor;
        this.numClockCycle = numClockCycle;
        this.filePath = filePath;
        this.processors = new ArrayList<>();
        this.clockCycles = new ArrayList<>();
        this.queue = new PriorityQueue<>();
        this.scheduler = new SchedulerImpl();
        initClockCycles();
        initProcessors();
    }

    private void initClockCycles() {
        for (int i = 1; i <= numClockCycle; i++) {
            clockCycles.add(new Clock("C" + i, i));
        }
    }

    private void initProcessors() {
        for (int i = 1; i <= numProcessor; i++) {
            processors.add(new Processor("P" + i, true));
        }
    }

    public void start() {
        List<Task> inputTasks = extractTasksFromFile();

        for (Clock clock : clockCycles) {
            int clockTime = clock.clockTick(1000);
            enqueueTasksAtClockTime(clockTime, inputTasks);
            if (!queue.isEmpty()) {
                scheduler.assignTasksToProcessors(queue, processors, clockTime);
            }
            printProcess(clock);
            clearFinishedProcessors(clockTime);
        }
    }

    public void enqueueTasksAtClockTime(long clockTime, List<Task> tasks) {
        for (Task task : tasks) {
            if (task.getInitialTime() == clockTime) {
                queue.add(task);
            }
        }
    }

    private void clearFinishedProcessors(int clockTime) {
        for (Processor processor : processors) {
            if (!processor.isIdle() && processor.getTask().getEndTask() == clockTime) {
                processor.setIdle(true);
                processor.setTask(null);
            }
        }
    }

    private List<Task> extractTasksFromFile() {

        int initTime, execTime, priority;

        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            this.numOfTasks = scanner.nextInt();
            int counter = 1;
            while (scanner.hasNextLine()) {
                initTime = scanner.nextInt();
                execTime = scanner.nextInt();
                priority = scanner.nextInt();
                Task task = new Task("T" + counter++, initTime, execTime, priority);
                tasks.add(task);
            }
            return tasks;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    private void printProcess(Clock clockTime) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println(String.format("%-10s%-15s%-20s%-20s%-10s", "Clock", "Processor", "Current Task", "Execution Time", "State"));
        System.out.println("---------------------------------------------------------------------------");
        System.out.print(String.format("%-10s", clockTime.getClockId()));
        boolean init = true;
        for (Processor processor : processors) {
            if (init) {
                init = false;
                if (processor.isIdle()) {
                    System.out.println(String.format("%-15s%-20s%-20s%-10s", processor.getProcessorId(), "", "", ProcessorState.IDLE));
                    continue;
                }
                System.out.println(String.format("%-15s%-20s%-20s%-10s", processor.getProcessorId(),
                        processor.getTask().getTaskId(), processor.getTask().getExecutionTime(), ProcessorState.ACTIVE));
                continue;
            }
            if (processor.isIdle()) {
                System.out.println(String.format("%-10s%-15s%-20s%-20s%-10s", "", processor.getProcessorId(), "", "", ProcessorState.IDLE));
                continue;
            }
            System.out.println(String.format("%-10s%-15s%-20s%-20s%-10s", "", processor.getProcessorId(),
                    processor.getTask().getTaskId(), processor.getTask().getExecutionTime(), ProcessorState.ACTIVE));

        }
        long idleProcessors = processors.stream().filter(Processor::isIdle).count();
        System.out.println();
        System.out.println("# of Waiting Tasks : " + queue.size());
        System.out.println("# of Idle Processors : " + idleProcessors);
        System.out.println("# of Active Processors : " + (processors.size() - idleProcessors));
        System.out.println("\n\n");

    }
}
