package ru.itis.javalab.services;

public interface LoginService {
    boolean authenticate(String email, String password);
}

