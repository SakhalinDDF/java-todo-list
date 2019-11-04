package com.todo.repository;

import com.todo.model.Task;
import com.todo.model.User;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findAllByUser(User user, Pageable pageable);
}
