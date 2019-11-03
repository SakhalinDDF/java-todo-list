package com.todo.model;

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
    @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime created_at;

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
        return created_at;
    }

    public enum Status {
        completed,
        uncompleted;
    }
}
