package com.todo;

import com.todo.base.TaskControllerLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public TaskControllerLogger taskControllerLogger() {
    return new TaskControllerLogger();
  }
}
