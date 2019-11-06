package com.todo.service;

import com.todo.entity.Task;
import com.todo.entity.User;
import com.todo.specification.TaskSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
  public Task find(User user, Long id);

  public Page<Task> findAll(Specification<Task> specification, Pageable pageable);

  public Task create(User user, String name);

  public Task update(User user, Long id, String name, String status);

  public void delete(User user, Long id);
}
