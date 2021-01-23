package ru.itiis.javalab.repositories;



import ru.itiis.javalab.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findOneByEmail(String email);
}

