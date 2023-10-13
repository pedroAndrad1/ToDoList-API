package com.pedroandrad.ToDoList.task;

import com.pedroandrad.ToDoList.user.UserModel;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime startsAt;
    private LocalDateTime endAt;
    private String priority;

    @ManyToOne()
    UserModel user;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
