package ru.aston.decorator;

public class WhippedCream extends CoffeeDecorator {
    public WhippedCream(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 50;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + взбитые сливки";
    }
}