package com.pedroandrad.ToDoList.task;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class TaskDto{
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startsAt;
    private LocalDateTime endAt;
    private String priority;
}
