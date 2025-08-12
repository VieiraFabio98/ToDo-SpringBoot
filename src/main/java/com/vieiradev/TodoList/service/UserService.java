package com.vieiradev.TodoList.service;

import com.vieiradev.TodoList.model.User;
import com.vieiradev.TodoList.repository.UserRepository;
import com.vieiradev.TodoList.valitadotor.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserValidator validator;

//    public UserService(UserRepository repository, UserValidator validator){
//        this.repository = repository;
//        this.validator = validator;
//    }

    public User save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        validator.validate(user);
        return repository.save(user);
    }

    public Optional<User> get(UUID id) {
        return repository.findById(id);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public List<User> list(String name, String email) {
//        if(name != null && email != null) {
//            return repository.findByNameAndEmail(name, email);
//        }

        if(name != null) {
            return repository.findByName(name);
        }

        if(email != null) {
            return repository.findByEmail(email);
        }

        return repository.findAll();
    }

    public List<User> searchByExample(String name, String email) {
        var user = new User();
        user.setName(name);
        user.setEmail(email);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> userExample = Example.of(user, matcher);

        return repository.findAll(userExample);
    }

    public void update(User user){
        validator.validate(user);
        repository.save(user);
    }

}
