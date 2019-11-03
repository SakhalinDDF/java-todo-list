package com.todo.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.IOException;
import java.time.OffsetDateTime;

@Entity(name = "user")
@Table(
        schema = "public",
        name = "user",
        indexes = {
                @Index(name = "user_idx_login", columnList = "login", unique = true),
                @Index(name = "user_idx_auth_token", columnList = "auth_token", unique = true),
                @Index(name = "user_idx_status", columnList = "status")
        }
)
@JsonSerialize(using = User.Serializer.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String login;

    @Column(name = "auth_token", columnDefinition = "VARCHAR(36) NOT NULL")
    private String authToken;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) NOT NULL")
    private Status status = Status.active;

    @CreationTimestamp
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    protected User() {
    }

    public User(String login, String authToken) {
        this.login = login;
        this.authToken = authToken;
    }

    public int getId() {
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
        public void serialize(User value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
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
