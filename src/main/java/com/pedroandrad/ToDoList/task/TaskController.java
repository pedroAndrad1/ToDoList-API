package com.pedroandrad.ToDoList.task;

import com.pedroandrad.ToDoList.user.IUserRepository;
import com.pedroandrad.ToDoList.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity createTask(@RequestBody TaskDto taskDto, HttpServletRequest request) {
        if(!isDatesValids(taskDto)){
            throw new HttpMessageNotReadableException(
                    "A data final não pode ser anterior a data de hoje ou a data inicial"
            );
        }
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

    private boolean isDatesValids(TaskDto taskDto){
        if(taskDto.getEndAt().isBefore(LocalDateTime.now())) return false;
        if(taskDto.getEndAt().isBefore(taskDto.getStartsAt())) return false;

        return  true;
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
            @RequestBody TaskDto taskDto,
            @PathVariable UUID id,
            HttpServletRequest request
    ) {
        Optional<TaskModel> taskToUpdate = taskRepository.findById(id);
        if (!taskToUpdate.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if(!compareUserNames(
                taskToUpdate.get().getUser().getUserName(),
                request.getAttribute("userName").toString()))
        {
            throw new SecurityException("Esta task não pertence a esse usuário");
        }

        TaskModel updatedTask = taskMapper.updateTaskFromDTO(taskDto, taskToUpdate.get());
        try {
            TaskModel savedTask = taskRepository.save(updatedTask);
            return ResponseEntity.status(HttpStatus.OK).body(savedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean compareUserNames(String userName1, String userName2){
        return userName1.equals(userName2);
    }

}
