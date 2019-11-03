package com.todo.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.IOException;
import java.time.OffsetDateTime;

@Entity(name = "task")
@Table(
        schema = "public",
        name = "task",
        indexes = {
                @Index(name = "task_idx_user_id", columnList = "user_id"),
                @Index(name = "task_idx_status", columnList = "status"),
                @Index(name = "task_idx_created_at", columnList = "created_at")
        }
)
@JsonSerialize(using = Task.Serializer.class)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            columnDefinition = "INT4 NOT NULL",
            foreignKey = @ForeignKey(
                    name = "task_fkey_user_id",
                    value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "FOREIGN KEY(\"user_id\") REFERENCES \"public\".\"user\" (\"id\") ON UPDATE CASCADE ON DELETE CASCADE"
            )
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) NOT NULL")
    private Status status = Status.uncompleted;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @CreationTimestamp
    @Column(
            name = "created_at",
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP"
    )
    private OffsetDateTime createdAt;

    protected Task() {
    }

    public Task(String name) {
        this.name = name;
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

    public enum Status {
        completed,
        uncompleted;
    }

    public static class Serializer extends JsonSerializer<Task> {
        @Override
        public void serialize(Task value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();

            gen.writeNumberField("id", value.getId());
            gen.writeStringField("name", value.getName());
            gen.writeStringField("status", value.getStatus().toString());
            gen.writeStringField("created_at", value.getCreatedAt().toString());

            gen.writeEndObject();
        }
    }
}
