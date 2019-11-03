package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.todo.model.Task;
import com.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping(value = "/listing")
    public ObjectNode listing(@RequestParam(required = false) String status) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        ArrayNode list = mapper.createArrayNode();

        result.set("models", list);

        return result;
    }

    @GetMapping
    public Task view(@RequestParam(required = true) int id) {
        return service.find(id);
    }

    @PostMapping
    public Task post(@RequestParam String name) {
        return service.create(name);
    }

    @PutMapping
    public Task put(@RequestParam int id, @RequestParam(required = false) String name, @RequestParam(required = false) String status) throws HttpClientErrorException {
        return service.update(id, name, status);
    }

    @DeleteMapping
    public boolean delete(@RequestParam int id) {
        service.delete(id);

        return true;
    }
}
