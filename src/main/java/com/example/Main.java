package com.example;

import com.example.deadlock.SharedResource;
import com.example.thread.Consumer;
import com.example.thread.ConsumerWithLock;
import com.example.thread.Producer;
import com.example.thread.ProducerWithLock;
import com.example.threadpool.CustomThreadPool;
import com.example.threadpool.Worker;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        ReentrantLock lock = new ReentrantLock();
//        executorService.submit(new Producer(queue));
//        executorService.submit(new Producer(queue));
//        executorService.submit(new Producer(queue));
//        executorService.submit(new Consumer(queue));
//        executorService.submit(new Consumer(queue));
//        executorService.submit(new ProducerWithLock(lock,queue));
//        executorService.submit(new ProducerWithLock(lock,queue));
//        executorService.submit(new ProducerWithLock(lock,queue));
//        executorService.submit(new ConsumerWithLock(lock,queue));
//        executorService.submit(new ConsumerWithLock(lock,queue));
//        executorService.shutdown();
       // deadLock();
//        SharedResource resource1 = new SharedResource();
//        SharedResource resource2 = new SharedResource();
//        Thread thread1 = new Thread(()-> {
//           resource1.methodA(resource2);
//        });
//        Thread thread2 = new Thread(()-> {
//            resource2.methodB(resource1);
//        });
//        thread1.start();
//        thread2.start();
//        thread2.join(1000);
//        thread1.join(1000);
        //threadPoolDemo();
        workerThreadDemo();
    }

    public static void deadLock(){
        ExecutorService executor = Executors.newFixedThreadPool(4);
        SharedResource resource1 = new SharedResource();
        SharedResource resource2 = new SharedResource();
        executor.submit(()-> {
            resource1.methodA(resource2);
        });
        executor.submit(()-> {
            resource2.methodB(resource1);
        });

        executor.shutdown();
    }

    public static void threadPoolDemo() throws InterruptedException {
        CustomThreadPool threadPool = new CustomThreadPool(4);
        for(int i = 0; i < 10; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                System.out.println("Thread ["+ Thread.currentThread().getName()+"] is running. Task "+ finalI);
                try {
                    System.out.println("Thread ["+ Thread.currentThread().getName()+"] is sleeping. Task "+ finalI);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("[threadPoolDemo] Thread ["+ Thread.currentThread().getName()+"] is getting interrupt signal. Task "+ finalI);
                    Thread.currentThread().interrupt();
                    //throw new RuntimeException(e);
                }
                System.out.println("Thread ["+ Thread.currentThread().getName()+"] has finished. Task "+ finalI);
            });
        }
        threadPool.shutdown();
    }

    public static void workerThreadDemo() throws InterruptedException {
        LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<>();
        taskQueue.offer(() -> {
            System.out.println("Task 1 is running.");
        });
        Worker worker = new Worker(taskQueue);
        worker.start();
        Thread.sleep(2000);
        worker.interrupt();
        worker.join();
    }
}