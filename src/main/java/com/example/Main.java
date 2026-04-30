package com.example;

import com.example.thread.Consumer;
import com.example.thread.ConsumerWithLock;
import com.example.thread.Producer;
import com.example.thread.ProducerWithLock;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        ReentrantLock lock = new ReentrantLock();
//        executorService.submit(new Producer(queue));
//        executorService.submit(new Producer(queue));
//        executorService.submit(new Producer(queue));
//        executorService.submit(new Consumer(queue));
//        executorService.submit(new Consumer(queue));
        executorService.submit(new ProducerWithLock(lock,queue));
        executorService.submit(new ProducerWithLock(lock,queue));
        executorService.submit(new ProducerWithLock(lock,queue));
        executorService.submit(new ConsumerWithLock(lock,queue));
        executorService.submit(new ConsumerWithLock(lock,queue));
        executorService.shutdown();
    }
}