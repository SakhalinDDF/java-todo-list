package com.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class MainPageController {
    @GetMapping
    public String index() throws IOException {
        return "Hello world!";
    }
}
