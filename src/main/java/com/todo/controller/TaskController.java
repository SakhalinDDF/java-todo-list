package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping
    public ObjectNode index(@RequestParam(required = false) String status) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        ArrayNode list = mapper.createArrayNode();

        result.set("models", list);

        return result;
    }

    @PostMapping
    public ObjectNode post(@RequestParam String name) {
        return service.create(name).buildObjectNode();
    }

    @PutMapping
    public ObjectNode put(@RequestParam int id, @RequestParam(required = false) String name, @RequestParam(required = false) String status) throws HttpClientErrorException {
        return service.update(id, name, status).buildObjectNode();
    }

    @DeleteMapping
    public ObjectNode delete(@RequestParam int id) {
        service.delete(id);

        return new ObjectMapper().createObjectNode();
    }
}
