package com.vieiradev.TodoList.repository;

import com.vieiradev.TodoList.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByName(String name);
    List<User> findByEmail(String email);
//    List<User> findByNameAndEmail(String name, String email);

    Optional<User> findByNameAndEmail(String name, String email);
}
