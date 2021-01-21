package caster;

import java.lang.reflect.Field;

public interface Castable {
    String cast(Field field);

    boolean isSupport(Field field);
}
