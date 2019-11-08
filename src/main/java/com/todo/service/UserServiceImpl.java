package com.todo.service;

import com.todo.base.NotFoundException;
import com.todo.entity.User;
import com.todo.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User findByID(Long id) {
    Optional<User> optionalUser = repository.findById(id);

    if (optionalUser.isEmpty()) {
      throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found");
    }

    return optionalUser.get();
  }

  @Override
  public User findByAuthToken(String authToken) {
    User user = repository.findByAuthToken(authToken);

    if (user == null) {
      throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found");
    }

    return user;
  }

  @Override
  public User register(String login) {
    User user = new User(login, UUID.randomUUID().toString());

    this.repository.save(user);

    return user;
  }
}
