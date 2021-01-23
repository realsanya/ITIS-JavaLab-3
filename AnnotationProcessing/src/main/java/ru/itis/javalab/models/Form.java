package ru.itis.javalab.models;

public class Form {
    String action;
    String method;

    public Form(String action, String method) {
        this.action = action;
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public String getMethod() {
        return method;
    }
}
