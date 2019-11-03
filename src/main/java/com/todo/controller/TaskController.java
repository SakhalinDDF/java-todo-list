package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/task")
public class TaskController {
    private ObjectMapper mapper;
    private TaskRepository repository;

    @GetMapping
    public ObjectNode index(@RequestParam(required = false) String status) {
        ObjectNode result = mapper.createObjectNode();
        ArrayNode list = mapper.createArrayNode();

        result.set("models", list);

        return result;
    }

    @PostMapping
    public ObjectNode post(@RequestParam String name) {
        Task task = new Task(name);

        repository.save(task);

        return buildObjectNode(task.getId());
    }

    @PutMapping
    public ObjectNode put(@RequestParam int id, @RequestParam(required = false) String name, @RequestParam(required = false) String status) throws HttpClientErrorException {
        Task task = repository.findById(id);

        if (task == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Task not found");
        }

        if (name != null) {
            task.setName(name);
        }

        if (status != null) {
            task.setStatus(Task.Status.valueOf(status));
        }

        repository.save(task);

        return buildObjectNode(id);
    }

    @DeleteMapping
    public ObjectNode delete(@RequestParam int id) {
        Task task = repository.findById(id);

        if (task == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Task not found");
        }

        repository.delete(task);

        return mapper.createObjectNode();
    }

    private ObjectNode buildObjectNode(int task_id) {
        Task task = repository.findById(task_id);

        return buildObjectNode(task);
    }

    private ObjectNode buildObjectNode(Task task) {
        ObjectNode result = mapper.createObjectNode();

        result.put("id", task.getId());
        result.put("name", task.getName());
        result.put("status", task.getStatus().toString());
        result.put("created_at", task.getCreatedAt().toString());

        return result;
    }

    @Bean
    public CommandLineRunner init(TaskRepository repository) {
        return (args) -> {
            this.mapper = new ObjectMapper();
            this.repository = repository;
        };
    }
}
