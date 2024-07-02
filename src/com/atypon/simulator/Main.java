package com.atypon.simulator;

public class Main {

    public static void main(String[] args) {
        //Processor Execution Simulator
        Simulator simulator = new Simulator(4, 10, "tasks.txt");
        simulator.start();


    }
}