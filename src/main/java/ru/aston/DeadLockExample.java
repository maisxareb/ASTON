package ru.aston;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockExample {
    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                lock1.lock();
                System.out.println("Thread 1: acquired lock1");
                try {
                    Thread.sleep(50);
                    lock2.lock();
                    System.out.println("Thread 1: acquired lock2");
                    lock2.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock1.unlock();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                lock2.lock();
                System.out.println("Thread 2: acquired lock2");
                try {
                    Thread.sleep(50);
                    lock1.lock();
                    System.out.println("Thread 2: acquired lock1");
                    lock1.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock2.unlock();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}