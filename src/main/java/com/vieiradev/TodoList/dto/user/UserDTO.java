package com.vieiradev.TodoList.dto.user;

import com.vieiradev.TodoList.model.User;

public record UserDTO(
        String name,
        String email,
        String password
) {
    public User mapping() {
        User user = new User();

        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);

        return user;
    }
}
