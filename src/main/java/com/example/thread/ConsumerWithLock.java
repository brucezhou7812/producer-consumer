package com.example.thread;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerWithLock implements Runnable{
    private final ReentrantLock lock;
    private final Queue<Integer> queue;
    private final Condition notEmpty;
    public ConsumerWithLock(ReentrantLock lock, Queue<Integer> queue){
        this.lock = lock;
        this.queue = queue;
        this.notEmpty = lock.newCondition();
    }
    @Override
    public void run() {
        while(queue != null){
            lock.lock();
            try {
                if (!queue.isEmpty()) {
                    int num = queue.poll();
                    System.out.println("Consumer[" + Thread.currentThread().getName() + "] has consumed an element: " + num);
                    notEmpty.signalAll();
                    Thread.sleep(1000);
                } else {
                    System.out.println("Consumer[" + Thread.currentThread().getName() + "] is waiting...");
                }
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
        }
    }
}
