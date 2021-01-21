package ru.itis.javalab.models;

import java.util.List;

public class Form {
    private String action;
    private String method;
    private List<Input> inputs;

    public Form() {
    }

    public Form(String action, String method, List<Input> inputs) {
        this.action = action;
        this.method = method;
        this.inputs = inputs;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

}
