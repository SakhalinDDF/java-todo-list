package com.todo.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "task")
@Table(schema = "public", name = "task")
@JsonSerialize(using = Task.Serializer.class)
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column
  private Status status = Status.uncompleted;

  @Column
  private String name;

  @CreationTimestamp
  @Column(name = "created_at", insertable = false, updatable = false)
  private OffsetDateTime createdAt;

  protected Task() {
  }

  public Task(User user, String name) {
    this.user = user;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public enum Status {
    completed,
    uncompleted;
  }

  public static class Serializer extends JsonSerializer<Task> {
    @Override
    public void serialize(Task value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeStartObject();

      gen.writeNumberField("id", value.getId());
      gen.writeStringField("name", value.getName());
      gen.writeStringField("status", value.getStatus().toString());
      gen.writeStringField("created_at", value.getCreatedAt().toString());

      gen.writeEndObject();
    }
  }
}
