package utils;

import annotations.Constraint;
import annotations.Table;

import java.lang.reflect.Field;

public class SQLUtils {
    public static String recognizeConstraints(Field field) {
        StringBuilder sql = new StringBuilder();
        Constraint constraint = field.getAnnotation(Constraint.class);
        if (constraint != null) {
            if (constraint.pk()) sql.append(" PRIMARY KEY ");
            if (constraint.uniq()) sql.append(" UNIQUE ");
            if (constraint.notNull()) sql.append(" NOT NULL ");
        }
        return sql.toString();
    }


    public static String recognizeTableName(Class<?> table) {
        String tableName;
        Table annotation = table.getAnnotation(Table.class);
        if (annotation.name().isEmpty()) {
            tableName = table.getSimpleName();
        } else {
            tableName = annotation.name();
        }
        return tableName;
    }
}
