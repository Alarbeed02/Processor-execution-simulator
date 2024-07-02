package com.atypon.simulator;

public class Processor {
    private final String processorId;
    private boolean idle;
    private Task task;

    public Processor(String processorId, boolean idle) {
        this.processorId = processorId;
        this.idle = idle;
    }

    public String getProcessorId() {
        return processorId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }


    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }


}