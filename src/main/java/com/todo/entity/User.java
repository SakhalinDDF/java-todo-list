package com.todo.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "user")
@Table(schema = "public", name = "user")
@JsonSerialize(using = User.Serializer.class)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String login;

  @Column(name = "auth_token")
  private String authToken;

  @Enumerated(EnumType.STRING)
  @Column
  private Status status = Status.active;

  @CreationTimestamp
  @Column(name = "created_at", insertable = false, updatable = false)
  private OffsetDateTime createdAt;

  protected User() {
  }

  public User(String login, String authToken) {
    this.login = login;
    this.authToken = authToken;
  }

  public Long getId() {
    return id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getAuthToken() {
    return authToken;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public enum Status {
    active,
    inactive;
  }

  public static class Serializer extends JsonSerializer<User> {
    @Override
    public void serialize(User value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeStartObject();

      gen.writeNumberField("id", value.getId());
      gen.writeStringField("login", value.getLogin());
      gen.writeStringField("auth_token", value.getAuthToken());
      gen.writeStringField("status", value.getStatus().toString());
      gen.writeStringField("created_at", value.getCreatedAt().toString());

      gen.writeEndObject();
    }
  }
}
