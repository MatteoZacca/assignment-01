package pcd.ass01.workers;

import pcd.ass01.BoidsModel;
import pcd.ass01.Boid;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class BoidsUpdateExecutor {
    private ExecutorService executor;
    private int numTasks;


    public BoidsUpdateExecutor() {
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }

    public void compute(final BoidsModel model) {
        this.numTasks = model.getBoids().size();

        List<Future<Void>> reads = new LinkedList<>();

        for (int taskIndex = 0; taskIndex < this.numTasks; taskIndex++) {
            Boid boid = model.getBoids().get(taskIndex);
            Future<Void> res = executor.submit(new ReadTask(boid, model));
            reads.add(res);
        }

        reads.forEach(readFuture -> {
            try {
                readFuture.get(); // chiamando get() su ogni Future, il codice si assicura che
                // tutti i task di lettura siano completati prima di procedere alla fase
                // successiva
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        List<Future<Void>> writes = new LinkedList<>();

        for (int taskIndex = 0; taskIndex < this.numTasks; taskIndex++) {
            Boid boid = model.getBoids().get(taskIndex);
            Future<Void> res = executor.submit(new WriteTask(boid, model));
            writes.add(res);
        }

        writes.forEach(writeFuture -> {
            try {
                writeFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
