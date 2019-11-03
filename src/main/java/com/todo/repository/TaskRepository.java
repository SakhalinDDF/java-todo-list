package com.todo.repository;

import com.todo.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Task findById(int id);
}
