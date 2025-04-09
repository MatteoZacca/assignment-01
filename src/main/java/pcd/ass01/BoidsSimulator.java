package pcd.ass01;

import pcd.ass01.workers.BoidsUpdateExecutor;

import java.util.Optional;

public class BoidsSimulator {

    private final BoidsUpdateExecutor boidsUpdateExecutor;

    private BoidsModel model;
    private long startTime;


    public BoidsSimulator(BoidsModel model) {
        this.model = model;
        this.boidsUpdateExecutor = new BoidsUpdateExecutor();
       startTime = System.currentTimeMillis();

    }

    public void runSimulation() {
        int iteration = 0;
        while (iteration++ < 1500) {
            boidsUpdateExecutor.compute(model);
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }

}


