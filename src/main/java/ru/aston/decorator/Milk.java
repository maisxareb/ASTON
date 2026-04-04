package ru.aston.decorator;

public class Milk extends CoffeeDecorator {
    public Milk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 30;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + молоко";
    }
}