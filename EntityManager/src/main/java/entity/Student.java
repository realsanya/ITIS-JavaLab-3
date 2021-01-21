package entity;

import annotations.Constraint;
import annotations.Table;
import com.sun.xml.internal.bind.v2.runtime.SchemaTypeTransducer;
import lombok.*;

import java.util.UUID;

@Data
@Table(name = "student")
public class Student {
    @Constraint(pk = true, uniq = true)
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;

    public Student(){

    }

    public Student(Integer id, String firstName, String lastName, Integer age){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
