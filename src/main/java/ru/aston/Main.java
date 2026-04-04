package ru.aston;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== DeadLock Example ===");
        System.out.println("(Программа зависнет - нажмите Ctrl+C для остановки)");

        System.out.println("\n=== LiveLock Example ===");
        LiveLockExample.main(args);
    }
}