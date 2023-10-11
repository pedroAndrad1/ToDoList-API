package com.pedroandrad.ToDoList.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "usuarios")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    @Column(unique = true)
    private String userName;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
