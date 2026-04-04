package ru.aston.chain;

public abstract class SupportHandler {
    protected SupportHandler next;

    public void setNext(SupportHandler next) {
        this.next = next;
    }

    public abstract void handle(String issue, int severity);

    protected void handleNext(String issue, int severity) {
        if (next != null) {
            next.handle(issue, severity);
        } else {
            System.out.println("Запрос не может быть обработан: " + issue);
        }
    }
}