package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.service.TaskService;
import com.todo.service.UserService;
import org.springframework.web.bind.annotation.*;
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
    public ObjectNode listing(
            @RequestParam String auth_token,
            @RequestParam(required = false) String status
    ) {
        User user = userService.findByAuthToken(auth_token);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        ArrayNode list = mapper.createArrayNode();

        result.set("models", list);

        return result;
    }

    @GetMapping
    public Task view(
            @RequestParam String auth_token,
            @RequestParam(required = true) int id
    ) {
        User user = userService.findByAuthToken(auth_token);

        return taskService.find(user, id);
    }

    @PostMapping
    public Task post(
            @RequestParam String auth_token,
            @RequestParam String name
    ) {
        User user = userService.findByAuthToken(auth_token);

        return taskService.create(user, name);
    }

    @PutMapping
    public Task put(
            @RequestParam String auth_token,
            @RequestParam int id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status
    ) throws HttpClientErrorException {
        User user = userService.findByAuthToken(auth_token);

        return taskService.update(user, id, name, status);
    }

    @DeleteMapping
    public boolean delete(
            @RequestParam String auth_token,
            @RequestParam int id
    ) {
        User user = userService.findByAuthToken(auth_token);

        taskService.delete(user, id);

        return true;
    }
}
