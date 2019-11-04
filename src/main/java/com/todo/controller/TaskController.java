package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.service.TaskService;
import com.todo.service.UserService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/task")
public class TaskController {
  private final TaskService taskService;
  private final UserService userService;

  public TaskController(TaskService taskService, UserService userService) {
    this.taskService = taskService;
    this.userService = userService;
  }

  @GetMapping(value = "/listing")
  public Page<Task> listing(
      @RequestParam String authToken,
      @RequestParam(required = false) String status,
      Pageable pageable
  ) {
    User user = userService.findByAuthToken(authToken);
    List<Task> tasks = taskService.findAll(user, pageable);

    return new PageImpl<>(tasks, pageable, tasks.size());
  }

  @GetMapping
  public Task view(
      @RequestParam String authToken,
      @RequestParam(required = true) Long id
  ) {
    User user = userService.findByAuthToken(authToken);

    return taskService.find(user, id);
  }

  @PostMapping
  public Task post(
      @RequestParam String authToken,
      @RequestParam String name
  ) {
    User user = userService.findByAuthToken(authToken);

    return taskService.create(user, name);
  }

  @PutMapping
  public Task put(
      @RequestParam String authToken,
      @RequestParam Long id,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String status
  ) throws HttpClientErrorException {
    User user = userService.findByAuthToken(authToken);

    return taskService.update(user, id, name, status);
  }

  @DeleteMapping
  public boolean delete(
      @RequestParam String authToken,
      @RequestParam Long id
  ) {
    User user = userService.findByAuthToken(authToken);

    taskService.delete(user, id);

    return true;
  }
}
