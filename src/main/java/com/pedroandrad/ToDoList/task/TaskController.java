package com.pedroandrad.ToDoList.task;

import com.pedroandrad.ToDoList.user.IUserRepository;
import com.pedroandrad.ToDoList.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    ITaskRepository taskRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    ITaskMapper taskMapper;

    @PostMapping("/create")
    public ResponseEntity<TaskModel> createTask(@RequestBody TaskDto taskDto, HttpServletRequest request) {
        try {
            TaskModel task = taskMapper.updateTaskFromDTO(taskDto, new TaskModel());
            UserModel user = userRepository.findByuserName(request.getAttribute("userName").toString());
            task.setUser(user);
            TaskModel createdTask = taskRepository.save(task);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskModel>> getUserTasks(HttpServletRequest request) {
        try {
            UserModel user = userRepository.findByuserName(request.getAttribute("userName").toString());
            Optional<List<TaskModel>> tasks = taskRepository.findByUser(user);
            if (!tasks.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }

            return ResponseEntity.status(HttpStatus.OK).body(tasks.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskModel> updateTask(
            @RequestBody
            TaskDto taskDto,
            @PathVariable UUID id
    ) {
        try {
            Optional<TaskModel> taskToUpdate = taskRepository.findById(id);
            if (!taskToUpdate.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            TaskModel updatedTask = taskMapper.updateTaskFromDTO(taskDto, taskToUpdate.get());
            TaskModel savedTask = taskRepository.save(updatedTask);
            return ResponseEntity.status(HttpStatus.OK).body(savedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
