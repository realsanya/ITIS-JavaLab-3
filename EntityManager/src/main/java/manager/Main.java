package manager;

import entity.Student;

import javax.sql.DataSource;
import java.util.UUID;


public class Main {
    public static void main(String[] args) {
        DataSource dataSource = SimpleDataSource.getDataSource();
        EntityManager entityManager = new EntityManager(dataSource);
        entityManager.createTable(entity.Student.class);
        entityManager.save(new Student(4, "Marsel", "Sidikov", 26));
        System.out.println(entityManager.findById("student", Student.class, 2));
    }
}
