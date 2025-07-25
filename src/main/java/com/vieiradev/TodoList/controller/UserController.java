package com.vieiradev.TodoList.controller;

import com.vieiradev.TodoList.dto.user.UserDTO;
import com.vieiradev.TodoList.dto.user.UserResponseDTO;
import com.vieiradev.TodoList.dto.user.UserResponseErrorDTO;
import com.vieiradev.TodoList.exceptions.DuplicatedRegisterException;
import com.vieiradev.TodoList.model.User;
import com.vieiradev.TodoList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

//    public UserController(UserService service) {
//        this.service = service;
//    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody UserDTO user) {
        try {
            User newUser = user.mapping();
            service.save(newUser);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newUser.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch(DuplicatedRegisterException dre) {
            var error = UserResponseErrorDTO.conflictError(dre.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(@PathVariable UUID id) {
        Optional<User> user = service.get(id);

        return (user.isPresent()) ? ResponseEntity.ok(UserResponseDTO.from(user.get())) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<User> user = service.get(id);

        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.delete(user.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email
    ){
        List<User> result = service.list(name, email);

        List<UserResponseDTO> users = result.stream()
                .map(UserResponseDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody UserDTO userDto) {
        try {
            Optional<User> userExists = service.get(id);

            if(userExists.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var user = userExists.get();
            user.setName(userDto.name());
            user.setEmail(userDto.email());
            user.setPassword(userDto.password());

            service.update(user);

            return ResponseEntity.noContent().build();
        } catch(DuplicatedRegisterException dre) {
            var error = UserResponseErrorDTO.conflictError(dre.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
}
