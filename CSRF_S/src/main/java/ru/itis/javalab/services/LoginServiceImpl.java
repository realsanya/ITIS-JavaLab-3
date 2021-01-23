package ru.itis.javalab.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;

import java.util.Optional;

@Component
public class LoginServiceImpl implements LoginService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean authenticate(String email, String password) {
        Optional<User> userOptional = usersRepository.findOneByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getPassword().equals(password);
        }
        return false;
    }
}

