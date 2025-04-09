package pcd.ass01.workers;

import pcd.ass01.BoidsModel;
import pcd.ass01.Boid;

import java.util.ArrayList;


public class BoidsUpdater {

    public BoidsUpdater() {}

    public void update(BoidsModel model) {
        ArrayList<Thread> readers = new ArrayList<Thread>();

        for (int i = 0; i < model.getBoids().size(); i++) {
            Boid boid = model.getBoids().get(i);
            Thread agent = Thread.ofVirtual().unstarted(() -> {
                boid.readNearbyBoids(model);
            });

            agent.start();
            readers.add(agent);
        }

        readers.forEach(it -> {
            try {
                it.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        ArrayList<Thread> writers = new ArrayList<Thread>();

        for (int i = 0; i < model.getBoids().size(); i++) {
            Boid boid = model.getBoids().get(i);
            Thread agent = Thread.ofVirtual().unstarted(() -> {
                boid.updateVelocity(model);
                boid.updatePos(model);
            });

            agent.start();
            writers.add(agent);
        }

        writers.forEach(it -> {
            try {
                it.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

}
