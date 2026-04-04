package ru.aston.decorator;

public class SimpleCoffee implements Coffee {
    @Override
    public double getCost() {
        return 100;
    }

    @Override
    public String getDescription() {
        return "Обычный кофе";
    }
}