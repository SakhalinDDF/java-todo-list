package com.todo.service;

import com.todo.model.Task;
import com.todo.model.User;
import com.todo.repository.TaskRepository;
import com.todo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Task find(User user, int id) {
        Task task = repository.findById(id);

        if (task == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Task not found");
        }

        return task;
    }

    @Transactional
    public Task create(User user, String name) {
        Task task = new Task(name);

        repository.save(task);

        return task;
    }

    @Transactional
    public Task update(User user, int id, String name, String status) {
        Task task = this.find(user, id);

        if (name != null) {
            task.setName(name);
        }

        if (status != null) {
            task.setStatus(Task.Status.valueOf(status));
        }

        repository.save(task);

        return task;
    }

    @Transactional
    public void delete(User user, int id) {
        repository.delete(this.find(user, id));
    }
}
