package caster;

import utils.SQLUtils;

import java.lang.reflect.Field;

public class IntegerCaster implements Castable{

    @Override
    public String cast(Field field) {
        return field.getName() + " INT " + SQLUtils.recognizeConstraints(field);
    }

    @Override
    public boolean isSupport(Field field) {
        return field.getType().getSimpleName().equals("Integer");
    }
}
