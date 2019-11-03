package com.todo.service;

import com.todo.model.Task;
import com.todo.model.User;
import com.todo.repository.TaskRepository;
import com.todo.repository.UserRepository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class TaskServiceImpl implements TaskService {
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;

  public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Task find(User user, int id) {
    Task task = taskRepository.findById(id);

    if (task == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Task not found");
    }

    return task;
  }

  @Override
  public List<Task> findAll(User user, Pageable pageable) {
    return taskRepository.findAllByUser(user, pageable);
  }

  @Override
  public Task create(User user, String name) {
    Task task = new Task(user, name);

    taskRepository.save(task);

    return task;
  }

  @Override
  public Task update(User user, int id, String name, String status) {
    Task task = this.find(user, id);

    if (name != null) {
      task.setName(name);
    }

    if (status != null) {
      task.setStatus(Task.Status.valueOf(status));
    }

    taskRepository.save(task);

    return task;
  }

  @Override
  public void delete(User user, int id) {
    taskRepository.delete(this.find(user, id));
  }
}
