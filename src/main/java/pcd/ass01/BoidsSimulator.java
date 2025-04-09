package pcd.ass01;

import pcd.ass01.workers.BoidsUpdateExecutor;

import java.util.Optional;

public class BoidsSimulator {

    private static final int FRAMERATE = 25;

    private final BoidsUpdateExecutor boidsUpdateExecutor;

    private BoidsModel model;
    private int framerate;
    private Optional<BoidsView> view;


    public BoidsSimulator(BoidsModel model) {
        this.model = model;
        this.boidsUpdateExecutor = new BoidsUpdateExecutor();
        view = Optional.empty();

    }

    public void attachView(BoidsView view) {
        this.view = Optional.of(view);
    }

    public void runSimulation() {
        while (true) {
            long t0 = System.currentTimeMillis();
            if (!model.isModelPaused()) { // Se il modello NON è in pausa, aggiorna la simulazione
                boidsUpdateExecutor.compute(model);

                if (view.isPresent()) {
                    view.get().update(framerate);
                    regulateFramerate(t0);
                }
            }
        }
    }

    private void regulateFramerate(long t0) {
        var framratePeriod = 1000 / FRAMERATE;
        long t1 = System.currentTimeMillis();
        var dtElapsed = t1 - t0;

        // System.out.println("dtElapsed: " + dtElapsed);

        if (dtElapsed < framratePeriod) {
            try {
                Thread.sleep(framratePeriod - dtElapsed);
            } catch (Exception ex) {}
            framerate = FRAMERATE;
        } else {
            framerate = (int) (1000 / dtElapsed);
        }
    }
}


