package ru.aston;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RaceConditionTest {

    @RepeatedTest(10)
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void testRaceConditionDetection() throws InterruptedException {
        AtomicInteger sharedResource = new AtomicInteger(0);
        int numberOfThreads = 5;
        int iterations = 1000;

        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    int current = sharedResource.get();
                    sharedResource.set(current + 1);
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join(2000);
        }

        int expected = numberOfThreads * iterations;
        int actual = sharedResource.get();

        if (actual != expected) {
            System.out.println("Race condition detected! Expected: " + expected +
                    ", Actual: " + actual);
        }

        assertNotEquals(expected, actual,
                "При отсутствии синхронизации race condition должен проявиться");
    }
}