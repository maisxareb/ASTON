package ru.aston;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class LiveLockExampleTest {

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void testLiveLockProgress() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        AtomicLong lastChangeTime = new AtomicLong(System.currentTimeMillis());
        AtomicBoolean running = new AtomicBoolean(true);

        Thread thread1 = new Thread(() -> {
            while (running.get()) {
                int current = counter.get();
                if (current % 2 == 0) {
                    counter.incrementAndGet();
                    lastChangeTime.set(System.currentTimeMillis());
                }
                Thread.yield();
            }
        });

        Thread thread2 = new Thread(() -> {
            while (running.get()) {
                int current = counter.get();
                if (current % 2 == 1) {
                    counter.incrementAndGet();
                    lastChangeTime.set(System.currentTimeMillis());
                }
                Thread.yield();
            }
        });

        thread1.start();
        thread2.start();

        Thread.sleep(1000);
        running.set(false);

        thread1.join(500);
        thread2.join(500);

        assertTrue(counter.get() > 0, "Счетчик должен увеличиваться: " + counter.get());
        System.out.println("Итоговое значение счетчика: " + counter.get());
    }

    @Test
    void testThreadsDontBlock() throws InterruptedException {
        LiveLockDetector detector = new LiveLockDetector();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                detector.operation1();
                Thread.yield();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                detector.operation2();
                Thread.yield();
            }
        });

        long startTime = System.currentTimeMillis();
        thread1.start();
        thread2.start();

        thread1.join(2000);
        thread2.join(2000);

        long duration = System.currentTimeMillis() - startTime;

        assertTrue(duration < 2000, "Потоки должны завершиться за 2 секунды");
        assertFalse(thread1.isAlive() && thread2.isAlive(), "Потоки должны завершиться");
    }

    static class LiveLockDetector {
        private int value = 0;

        public void operation1() {
            if (value == 0) {
                value = 1;
            }
        }

        public void operation2() {
            if (value == 1) {
                value = 0;
            }
        }

        public int getValue() {
            return value;
        }
    }
}
