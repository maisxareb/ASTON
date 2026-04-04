package ru.aston;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AlternatePrintingAdvanced {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static volatile int turn = 1;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (turn != 1) {
                        condition.await();
                    }
                    System.out.print("1 ");
                    turn = 2;
                    condition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (turn != 2) {
                        condition.await();
                    }
                    System.out.print("2 ");
                    turn = 1;
                    condition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}