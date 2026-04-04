package ru.aston;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class DeadLockExampleTest {

    @Test
    void testDeadLockOccurs() throws InterruptedException {
        AtomicBoolean thread1Finished = new AtomicBoolean(false);
        AtomicBoolean thread2Finished = new AtomicBoolean(false);
        AtomicBoolean deadlockDetected = new AtomicBoolean(false);

        Thread thread1 = new Thread(() -> {
            synchronized (DeadLockTestHelper.LOCK1) {
                try {
                    Thread.sleep(100);
                    synchronized (DeadLockTestHelper.LOCK2) {
                        thread1Finished.set(true);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (DeadLockTestHelper.LOCK2) {
                try {
                    Thread.sleep(100);
                    synchronized (DeadLockTestHelper.LOCK1) {
                        thread2Finished.set(true);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join(2000);
        thread2.join(2000);

        if (!thread1Finished.get() && !thread2Finished.get()) {
            deadlockDetected.set(true);
            System.out.println("Deadlock успешно обнаружен!");
        }

        assertTrue(deadlockDetected.get() || (!thread1Finished.get() && !thread2Finished.get()),
                "Должен быть deadlock, но потоки завершились");

        if (thread1.isAlive()) {
            thread1.interrupt();
        }
        if (thread2.isAlive()) {
            thread2.interrupt();
        }
    }

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void testThreadsAreAlive() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            synchronized (DeadLockTestHelper.LOCK1) {
                try {
                    Thread.sleep(500);
                    synchronized (DeadLockTestHelper.LOCK2) {
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (DeadLockTestHelper.LOCK2) {
                try {
                    Thread.sleep(500);
                    synchronized (DeadLockTestHelper.LOCK1) {
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread1.start();
        thread2.start();

        Thread.sleep(200);

        assertTrue(thread1.isAlive() && thread2.isAlive(),
                "Ожидается deadlock, оба потока должны быть живы");

        thread1.interrupt();
        thread2.interrupt();
        thread1.join(1000);
        thread2.join(1000);
    }
}

class DeadLockTestHelper {
    public static final Object LOCK1 = new Object();
    public static final Object LOCK2 = new Object();
}