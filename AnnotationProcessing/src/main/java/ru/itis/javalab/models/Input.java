package ru.itis.javalab.models;

public class Input {
    String type;
    String name;
    String placeholder;
    public Input(String type,String name,String placeholder){
        this.type = type;
        this.name = name;
        this.placeholder = placeholder;
    }

    public String getName() {
        return name;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getType() {
        return type;
    }
}
