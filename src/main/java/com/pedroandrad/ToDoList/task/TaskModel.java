package com.pedroandrad.ToDoList.task;

import com.pedroandrad.ToDoList.user.UserModel;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.converter.HttpMessageNotReadableException;

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

    public void setTitle(String title) throws HttpMessageNotReadableException{
       if( title.length() > 50)
           throw new HttpMessageNotReadableException("O title deve ter no máximo 50 caracteres");

       this.title = title;
    }
}
