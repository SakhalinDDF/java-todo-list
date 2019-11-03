package com.todo.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity(name = "task")
@Table(
        schema = "public",
        name = "task",
        indexes = {
                @Index(name = "task_idx_status", columnList = "status"),
                @Index(name = "task_idx_created_at", columnList = "created_at")
        }
)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(32) NOT NULL")
    private Status status;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    public enum Status {
        completed,
        uncompleted;
    }

    protected Task() {
    }

    public Task(String name) {
        this.name = name;
        this.status = Status.uncompleted;
    }

    public int getId() {
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

    public ObjectNode buildObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("id", id);
        node.put("name", name);
        node.put("status", status.toString());
        node.put("created_at", createdAt.toString());

        return node;
    }
}
