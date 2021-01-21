package caster;

import utils.SQLUtils;

import java.lang.reflect.Field;

public class StringCaster implements Castable {
    @Override
    public String cast(Field field) {
        return field.getName() + " VARCHAR(45) " + SQLUtils.recognizeConstraints(field);
    }

    @Override
    public boolean isSupport(Field field) {
        return field.getType().getSimpleName().equals("String");
    }
}
