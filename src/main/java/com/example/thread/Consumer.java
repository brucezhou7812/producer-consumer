package com.example.thread;

import java.util.Queue;

public class Consumer implements Runnable{
    private final Queue<Integer> queue;
    public Consumer(Queue<Integer> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        while(queue != null){
            synchronized(queue){
                if(!queue.isEmpty()){
                    int num = queue.poll();
                    System.out.println("Consumer["+Thread.currentThread().getName()+"] has consumed an element: " + num);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    queue.notifyAll();
                }else{
                    System.out.println("Consumer["+Thread.currentThread().getName()+"] is waiting...");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
