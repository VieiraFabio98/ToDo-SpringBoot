package com.vieiradev.TodoList.dto.task;

import com.vieiradev.TodoList.model.User;

public record TaskDTO(
        String title,
        String description,
        User user
) {
}
