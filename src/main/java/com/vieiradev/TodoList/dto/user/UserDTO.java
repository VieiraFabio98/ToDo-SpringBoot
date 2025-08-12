package com.vieiradev.TodoList.dto.user;

import com.vieiradev.TodoList.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 100, message = "campo fora do tamanho padrão")
        String name,
        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Digite um email válido")
        String email,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 6, max = 64, message = "campo fora do tamanho padrão")
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
