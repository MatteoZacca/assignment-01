package pcd.ass01;

import java.util.Optional;

public class BoidsSimulator {

    private BoidsModel model;
    private long startTime;

    public BoidsSimulator(BoidsModel model) {
        startTime = System.currentTimeMillis();
        this.model = model;
    }

    public void runSimulation() {
        int iteration = 0;
        while (iteration++ < 1500) {
            var boids = model.getBoids();

            for (Boid boid : boids) {
                boid.updateVelocity(model);
            }

            for (Boid boid : boids) {
                boid.updatePos(model);
            }
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }
}


