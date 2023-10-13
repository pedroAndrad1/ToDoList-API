package com.pedroandrad.ToDoList.task;

import com.pedroandrad.ToDoList.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    Optional<List<TaskModel>> findByUser(UserModel user);
}
