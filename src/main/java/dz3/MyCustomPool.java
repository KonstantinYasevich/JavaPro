package dz3;

import java.util.LinkedList;
import java.util.Queue;

public class MyCustomPool {

    private final Thread[] threads;
    private final Queue<Runnable> taskQueue;
    private boolean isShutdown;

    public MyCustomPool(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Размер пула не должен быть больше 0");
        }
        this.taskQueue = new LinkedList<>();
        this.threads = new Thread[capacity];
        this.isShutdown = false;

        for (int i = 0; i < capacity; i++) {
            threads[i] = new Worker("CustomPool-Thread-" + i);
            threads[i].start();
        }
    }

    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("Задача должна быть");
        }
        synchronized (taskQueue) {
            if (isShutdown) {
                throw new IllegalStateException("Работа пула потоков завершена. Добавление новых задач невозможно");
            }
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    public void shutdown() {
        synchronized (taskQueue) {
            isShutdown = true;
            taskQueue.notifyAll();
        }
    }

    public void awaitTermination() throws InterruptedException {
        for (Thread worker : threads) {
            worker.join();
        }
    }


    private class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            Runnable task;
            while (true) {
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    if (isShutdown && taskQueue.isEmpty()) {
                        return;
                    }
                    task = taskQueue.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.err.println("Ой проблема: " + e.getMessage());
                }
            }
        }
    }

}
