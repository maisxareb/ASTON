package ru.aston.chain;

public class JuniorSupport extends SupportHandler {
    @Override
    public void handle(String issue, int severity) {
        if (severity <= 3) {
            System.out.println("  Младший специалист обработал: " + issue);
        } else {
            handleNext(issue, severity);
        }
    }
}