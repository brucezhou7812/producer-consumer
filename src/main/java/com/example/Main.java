package com.example;

import com.example.thread.Consumer;
import com.example.thread.Producer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        executorService.submit(new Producer(queue));
        executorService.submit(new Producer(queue));
        executorService.submit(new Producer(queue));
        executorService.submit(new Consumer(queue));
        executorService.submit(new Consumer(queue));
        executorService.shutdown();
    }
}