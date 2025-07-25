package com.vieiradev.TodoList.repository;

import com.vieiradev.TodoList.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface TaskRepository extends JpaRepository<Task, UUID> {
}
