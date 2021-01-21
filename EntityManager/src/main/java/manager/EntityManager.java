package manager;

import caster.Castable;
import caster.IntegerCaster;
import caster.StringCaster;
import utils.SQLUtils;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class EntityManager {
    private DataSource dataSource;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private ArrayList<Castable> castables = new ArrayList<Castable>() {
        {
            add(new IntegerCaster());
        }

        {
            add(new StringCaster());
        }
    };

    public <T> void createTable(Class<T> entityClass) {
        StringBuilder SQL = new StringBuilder();
        SQL.append("CREATE TABLE IF NOT EXISTS ").append(SQLUtils.recognizeTableName(entityClass)).append(" (");

        Stream.of(entityClass.getDeclaredFields())
                .forEach(field -> {
                    SQL.append(castables.stream()
                            .filter(castable -> castable.isSupport(field))
                            .map(castable -> castable.cast(field))
                            .findFirst()
                            .orElse("")).append(", ")
                    ;
                });
        String finalSQL = SQL.substring(0, SQL.length() - 2) + ");";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(finalSQL);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void save(Object entity) {
        Class<?> classOfEntity = entity.getClass();
        StringBuilder SQL = new StringBuilder();
        SQL.append("INSERT INTO ").append(SQLUtils.recognizeTableName(classOfEntity)).append(" VALUES (");

        Field[] fields = classOfEntity.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            SQL.append("?");
            if (i + 1 != fields.length) {
                SQL.append(", ");
            }
        }
        SQL.append(");");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL.toString())) {

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                statement.setObject(i + 1, fields[i].get(entity));
            }
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T, ID> T findById(String tableName, Class<T> resultType, ID idValue) {
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT * FROM ").append(tableName).append(" WHERE id = ?");
        T result;

        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL.toString())) {

            Field id = resultType.getDeclaredField("id");
            id.setAccessible(true);

            statement.setObject(1, idValue);
            resultSet = statement.executeQuery();
            Constructor<T> constructor = resultType.getConstructor();
            result = constructor.newInstance();

            if (resultSet.next()) {
                for (Field field : resultType.getDeclaredFields()) {
                    field.setAccessible(true);
                    field.set(result, resultSet.getObject(field.getName()));
                }
            } else {
                result = null;
            }
            return result;
        } catch (SQLException | NoSuchFieldException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignore) {
                    //ignore
                }
            }
        }
    }


}
