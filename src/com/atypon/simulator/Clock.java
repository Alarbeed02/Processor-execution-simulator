package com.atypon.simulator;

public class Clock {

    private final String clockId;
    private final int clockCycle;

    public Clock(String clockId, int clockCycle) {
        this.clockId = clockId;
        this.clockCycle = clockCycle;
    }

    public int clockTick(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        return clockCycle;
    }

    public String getClockId() {
        return clockId;
    }
}