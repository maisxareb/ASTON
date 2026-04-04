package ru.aston.decorator;

public class Sugar extends CoffeeDecorator {
    public Sugar(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 10;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + сахар";
    }
}