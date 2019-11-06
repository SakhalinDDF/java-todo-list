package com.todo.specification;

import com.todo.entity.Task;
import com.todo.entity.TaskStatus;
import com.todo.entity.User;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification implements Specification<Task> {
  private TaskStatus status;

  private User user;

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (this.user != null) {
      predicates.add(criteriaBuilder.equal(root.get("user"), this.user));
    }

    if (this.status != null) {
      predicates.add(criteriaBuilder.equal(root.get("status"), this.status));
    }

    if (predicates.size() > 1) {
      return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }

    return predicates.get(0);
  }
}
