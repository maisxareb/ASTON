package ru.aston.chain;

public class SeniorSupport extends SupportHandler {
    @Override
    public void handle(String issue, int severity) {
        if (severity <= 7) {
            System.out.println("  Старший специалист обработал: " + issue);
        } else {
            handleNext(issue, severity);
        }
    }
}