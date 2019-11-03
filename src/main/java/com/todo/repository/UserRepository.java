package com.todo.repository;

import com.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    User findByAuthToken(String authToken);
}
