package ru.itis.javalab.models;

import java.io.Serializable;

public class Input implements Serializable {
    private String type;
    private String name;
    private String placeholder;


    public Input(String type, String name, String placeholder) {
        this.type = type;
        this.name = name;
        this.placeholder = placeholder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
