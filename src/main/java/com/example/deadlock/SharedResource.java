package com.example.deadlock;

public class SharedResource {
    public synchronized void methodA(SharedResource other){
        System.out.println("Thread " + Thread.currentThread().getName() + " is executing methodA");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        other.methodB(this);
    }

    public synchronized void methodB(SharedResource other){
        System.out.println("Thread " + Thread.currentThread().getName() + " is executing methodB");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        other.methodA(this);
    }
}
