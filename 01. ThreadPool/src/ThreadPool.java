import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ThreadPool {
    private Deque<Runnable> tasks;
    private PoolWorker[] pool;

    public ThreadPool(int threadsCount) {
        this.tasks = new ConcurrentLinkedDeque<>();
        this.pool = new PoolWorker[threadsCount];

        for (int i = 0; i < this.pool.length; i++) {
            this.pool[i] = new PoolWorker();
            this.pool[i].start();
        }
    }

    @SuppressWarnings("SynchronizeOnNonFinalField")
    public void submit(Runnable task) {
        synchronized (tasks) {
            tasks.addLast(task);
            tasks.notify();
        }
    }

    @SuppressWarnings({"InfiniteLoopStatement", "SynchronizeOnNonFinalField"})
    private class PoolWorker extends Thread {
        @Override
        public void run() {
            Runnable task;

            while (true) {

                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    task = tasks.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}
