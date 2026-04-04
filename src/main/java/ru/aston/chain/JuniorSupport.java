package ru.aston.chain;

public class JuniorSupport extends SupportHandler {
    private static final int MAX_SEVERITY_FOR_JUNIOR = 3;

    @Override
    public void handle(String issue, int severity) {
        if (severity <= MAX_SEVERITY_FOR_JUNIOR) {
            System.out.println("Младший специалист обработал: " + issue);
        } else {
            handleNext(issue, severity);
        }
    }
}