package com.example.thread;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerWithLock implements Runnable{
    private final ReentrantLock lock;
    private final Condition notFull;
    private final Queue<Integer> queue;
    public ProducerWithLock(ReentrantLock lock, Queue<Integer> queue){
        this.queue = queue;
        this.notFull = lock.newCondition();
        this.lock = lock;
    }
    @Override
    public void run() {
        while(queue != null){
           lock.lock();
           try {
               if (queue.size() < 10) {
                   int num = (int) (Math.random() * 100);
                   queue.add(num);
                   System.out.println("Producer[" + Thread.currentThread().getName() + "] has made an element: " + num);
                   notFull.signalAll();
                   Thread.sleep(1000);
               } else {
                   System.out.println("Producer[" + Thread.currentThread().getName() + "] is waiting...");
               }
           }catch(InterruptedException e){
               throw new RuntimeException(e);
           }finally{
               lock.unlock();
           }
        }
    }
}
