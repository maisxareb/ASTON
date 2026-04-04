package ru.aston;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class AlternatePrintingTest {

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void testAlternatePrintingOrder() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        final Object lock = new Object();
        final AtomicBoolean isFirstTurn = new AtomicBoolean(true);
        final AtomicBoolean running = new AtomicBoolean(true);

        Thread thread1 = new Thread(() -> {
            while (running.get()) {
                synchronized (lock) {
                    while (!isFirstTurn.get()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.print("1");
                    isFirstTurn.set(false);
                    lock.notify();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (running.get()) {
                synchronized (lock) {
                    while (isFirstTurn.get()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.print("2");
                    isFirstTurn.set(true);
                    lock.notify();
                }
            }
        });

        thread1.start();
        thread2.start();

        Thread.sleep(500);
        running.set(false);

        thread1.interrupt();
        thread2.interrupt();
        thread1.join(500);
        thread2.join(500);

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.length() >= 4, "Вывод должен содержать минимум 4 символа");

        for (int i = 0; i < output.length() - 1; i++) {
            char current = output.charAt(i);
            char next = output.charAt(i + 1);
            assertNotEquals(current, next, "Символы не должны повторяться подряд: " + output);
        }

        if (output.length() > 0) {
            assertEquals('1', output.charAt(0), "Вывод должен начинаться с 1");
        }

        System.out.println("Вывод программы: " + output.substring(0, Math.min(20, output.length())));
    }

    @Test
    void testOnlyOneAndTwoPrinted() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        final Object lock = new Object();
        final AtomicBoolean isFirstTurn = new AtomicBoolean(true);
        final AtomicBoolean running = new AtomicBoolean(true);

        Thread thread1 = new Thread(() -> {
            while (running.get()) {
                synchronized (lock) {
                    while (!isFirstTurn.get()) {
                        try {
                            lock.wait(10);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    System.out.print("1");
                    isFirstTurn.set(false);
                    lock.notify();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (running.get()) {
                synchronized (lock) {
                    while (isFirstTurn.get()) {
                        try {
                            lock.wait(10);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    System.out.print("2");
                    isFirstTurn.set(true);
                    lock.notify();
                }
            }
        });

        thread1.start();
        thread2.start();

        Thread.sleep(500);
        running.set(false);
        thread1.interrupt();
        thread2.interrupt();
        thread1.join(500);
        thread2.join(500);

        System.setOut(originalOut);
        String output = outContent.toString();

        for (char c : output.toCharArray()) {
            assertTrue(c == '1' || c == '2', "Должны печататься только 1 и 2, найдено: " + c);
        }

        System.out.println("Тест пройден. Вывод: " + output.substring(0, Math.min(20, output.length())));
    }
}