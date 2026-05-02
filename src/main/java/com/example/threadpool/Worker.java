package com.example.threadpool;

import java.util.concurrent.LinkedBlockingDeque;

public class Worker extends Thread{
    private final LinkedBlockingDeque<Runnable> taskQueue;
    public  final Runnable poisonPill = EmptyRunnable.poisonPill;
    public Worker(LinkedBlockingDeque<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }
    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try {
                Runnable task = taskQueue.take();
                if(task == poisonPill){
                    System.out.println("Worker["+this.getName()+"] is stopping...");
                    break;
                }
                task.run();
                //Thread.sleep(10000000);
            } catch (InterruptedException e){
                System.out.println("[Worker] Thread ["+ this.getName()+"] is getting interrupt signal. Task ");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Worker["+this.getName()+"] has stopped.");
    }
}
