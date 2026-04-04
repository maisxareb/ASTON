package ru.aston.strategy;

public class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void checkout(int amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Способ оплаты не выбран");
        }
        paymentStrategy.pay(amount);
    }
}