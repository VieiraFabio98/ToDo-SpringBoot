package com.vieiradev.TodoList.valitadotor;

import com.vieiradev.TodoList.exceptions.DuplicatedRegisterException;
import com.vieiradev.TodoList.model.User;
import com.vieiradev.TodoList.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserValidator {

    private UserRepository repository;

    public UserValidator(UserRepository repository) {
        this.repository = repository;
    }

    public void validate(User user) {
        if(UserAlreadyExists(user)) {
            throw new DuplicatedRegisterException("User already exists");
        }
    }

    private boolean UserAlreadyExists(User user) {
        Optional<User> userExists = repository.findByNameAndEmail(
                user.getName(), user.getEmail()
        );

        if(user.getId() == null) {
            return userExists.isPresent();
        }

        return userExists
                .map(existingUser -> !user.getId().equals(existingUser.getId()))
                .orElse(false);
    }
}
