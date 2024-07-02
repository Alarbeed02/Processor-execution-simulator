package com.atypon.simulator;

public class Task implements Comparable<Task> {

    private final String taskId;
    private int initialTime;
    private int executionTime;
    private int endTask;
    private int priority;


    public Task(String taskId, int initialTime, int executionTime, int priority) {
        this.taskId = taskId;
        this.initialTime = initialTime;
        this.executionTime = executionTime;
        this.priority = priority;
    }

    public String getTaskId() {
        return taskId;
    }

    public int getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(int initialTime) {
        this.initialTime = initialTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public boolean isPriority() {
        return (priority > 0);
    }


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getEndTask() {
        return endTask;
    }

    public void setEndTask(int endTask) {
        this.endTask = endTask;
    }

    @Override
    public int compareTo(Task o) {
        int result = o.priority - priority;
        if (result == 0) {
            return o.executionTime - executionTime;
        }
        return result;
    }


}