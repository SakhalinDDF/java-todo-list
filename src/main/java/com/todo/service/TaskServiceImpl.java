package com.todo.service;

import com.todo.entity.Task;
import com.todo.entity.TaskStatus;
import com.todo.entity.User;
import com.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

import com.todo.specification.TaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class TaskServiceImpl implements TaskService {
  private final TaskRepository repository;

  public TaskServiceImpl(TaskRepository repository) {
    this.repository = repository;
  }

  @Override
  public Task find(User user, Long id) {
    Optional<Task> taskOptional = repository.findById(id);

    if (taskOptional.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Task not found");
    }

    return taskOptional.get();
  }

  @Override
  public Page<Task> findAll(Specification<Task> specification, Pageable pageable) {
    return repository.findAll(specification, pageable);
  }

  @Override
  public Task create(User user, String name) {
    Task task = new Task(user, name);

    repository.save(task);

    return task;
  }

  @Override
  public Task update(User user, Long id, String name, String status) {
    Task task = this.find(user, id);

    if (name != null) {
      task.setName(name);
    }

    if (status != null) {
      task.setStatus(TaskStatus.valueOf(status));
    }

    repository.save(task);

    return task;
  }

  @Override
  public void delete(User user, Long id) {
    repository.delete(this.find(user, id));
  }
}
