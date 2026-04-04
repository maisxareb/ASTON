package ru.aston;

import java.util.concurrent.atomic.AtomicInteger;

public class LiveLockExample {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static volatile boolean running = true;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (running) {
                int current = counter.get();
                if (current % 2 == 0) {
                    System.out.println("Thread 1: Counter is " + current + ", incrementing...");
                    counter.incrementAndGet();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (running) {
                int current = counter.get();
                if (current % 2 == 1) {
                    System.out.println("Thread 2: Counter is " + current + ", incrementing...");
                    counter.incrementAndGet();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(2000);
            running = false;
            System.out.println("\nFinal counter value: " + counter.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}