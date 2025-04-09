package pcd.ass01;

import pcd.ass01.workers.BoidsUpdater;

import java.util.Optional;

public class BoidsSimulator {

    private final BoidsUpdater boidsUpdater;

    private BoidsModel model;

    private long startTime;


    public BoidsSimulator(BoidsModel model, int threadCount) {
        this.model = model;
        this.boidsUpdater = new BoidsUpdater(threadCount);
        startTime = System.currentTimeMillis();
    }

    public void runSimulation() {
        int iteration = 0;
        while (iteration++ < 1500) {
            boidsUpdater.update(model);
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}


