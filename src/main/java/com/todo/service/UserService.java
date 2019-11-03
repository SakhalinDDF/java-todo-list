package com.todo.service;

import com.todo.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  public User findByID(int id);

  public User findByAuthToken(String authToken);

  public User register(String login);
}
