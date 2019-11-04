package com.todo.service;

import com.todo.model.Task;
import com.todo.model.User;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
  public Task find(User user, Long id);

  public List<Task> findAll(User user, Pageable pageable);

  public Task create(User user, String name);

  public Task update(User user, Long id, String name, String status);

  public void delete(User user, Long id);
}
