package ru.aston.chain;

public class LeadSupport extends SupportHandler {
    @Override
    public void handle(String issue, int severity) {
        System.out.println("Руководитель отдела обработал: " + issue);
    }
}