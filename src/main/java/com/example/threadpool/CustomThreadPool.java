package com.example.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThreadPool {
    private final LinkedBlockingDeque<Runnable> taskQueue;
    private final Worker[] workers;
    private final Runnable poisonPill = EmptyRunnable.poisonPill;
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);
    public CustomThreadPool(int numThreads){
        this.taskQueue = new LinkedBlockingDeque<>();
        this.workers = new Worker[numThreads];
    }

    public void submit(Runnable task){
        if(isShutdown.get()){
            throw new IllegalStateException("Thread pool is shutdown, cannot accept new tasks.");
        }
        taskQueue.offer(task);
        for(int i = 0; i < workers.length; i++){
            Worker worker = workers[i];
            if(worker == null || !worker.isAlive()){
                Worker newWorker = new Worker(taskQueue);
                newWorker.start();
                workers[i] = newWorker;
                break;
            }
        }
    }

    public void interrupt(){
        isShutdown.set(true);
        for(Worker worker: workers){
            if(worker != null && worker.isAlive()) {
                worker.interrupt();
            }
        }
    }

    public void shutdown(){
        isShutdown.set(true);
        for(Worker worker: workers){
            if(worker != null && worker.isAlive()) {
                taskQueue.offer(poisonPill);
            }
        }
        for(Worker worker: workers){
            System.out.println("[CustomThreadPool] worker ["+worker.getName()+"] "+ "is" + (worker.isAlive()?" alive":" dead"));
            if(worker != null && worker.isAlive()){
                try {
                    System.out.println("[CustomThreadPool] Waiting for worker thread ["+ worker.getName()+"] to finish...");
                    worker.join();
                } catch (InterruptedException e) {
                    System.out.println("[CustomThreadPool] Thread ["+ Thread.currentThread().getName()+"] is getting interrupt signal.");
                    Thread.currentThread().interrupt();
                }
            }
        }

    }




}
