package com.example.thread;

import java.util.Queue;

public class Producer implements Runnable{
    private final Queue<Integer> queue;
    public Producer(Queue<Integer> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        while(queue != null){
            synchronized (queue){
                if(queue.size() < 10){
                    int num = (int)(Math.random() * 100);
                    queue.add(num);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Producer["+Thread.currentThread().getName()+"] has made an element: " + num);
                    queue.notifyAll();
                }else{
                    System.out.println("Producer["+Thread.currentThread().getName()+"] is waiting...");
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