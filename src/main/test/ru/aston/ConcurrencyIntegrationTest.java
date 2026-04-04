package ru.aston;

import org.junit.jupiter.api.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConcurrencyIntegrationTest {

    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        executorService = Executors.newFixedThreadPool(4);
    }

    @AfterEach
    void tearDown() {
        executorService.shutdownNow();
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testMultipleThreadsCoordination() throws InterruptedException, ExecutionException, BrokenBarrierException, TimeoutException {
        CountDownLatch latch = new CountDownLatch(2);
        CyclicBarrier barrier = new CyclicBarrier(3);

        Future<String> future1 = executorService.submit(() -> {
            try {
                barrier.await(1, TimeUnit.SECONDS);
                latch.countDown();
                return "Thread 1 completed";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Future<String> future2 = executorService.submit(() -> {
            try {
                barrier.await(1, TimeUnit.SECONDS);
                latch.countDown();
                return "Thread 2 completed";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        barrier.await(1, TimeUnit.SECONDS);

        boolean completed = latch.await(2, TimeUnit.SECONDS);
        assertTrue(completed, "Оба потока должны завершиться");

        assertEquals("Thread 1 completed", future1.get());
        assertEquals("Thread 2 completed", future2.get());
    }

    @Test
    void testThreadSafety() throws InterruptedException {
        Counter counter = new Counter();
        int numberOfThreads = 10;
        int numberOfIncrements = 1000;

        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < numberOfIncrements; j++) {
                    counter.increment();
                }
                latch.countDown();
            });
        }

        latch.await(5, TimeUnit.SECONDS);

        assertEquals(numberOfThreads * numberOfIncrements, counter.getValue(),
                "Счетчик должен быть потокобезопасным");
    }

    static class Counter {
        private final AtomicInteger value = new AtomicInteger(0);

        public void increment() {
            value.incrementAndGet();
        }

        public int getValue() {
            return value.get();
        }
    }
}