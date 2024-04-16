package com.kpi.journalrest.exception;

public class ElementNotFoundException extends RuntimeException{
    public ElementNotFoundException(String message) {
        super("Could not find " + message);
    }
}
