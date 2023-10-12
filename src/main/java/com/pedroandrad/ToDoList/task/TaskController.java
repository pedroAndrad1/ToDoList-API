package com.pedroandrad.ToDoList.task;

import com.pedroandrad.ToDoList.user.IUserRepository;
import com.pedroandrad.ToDoList.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    ITaskRepository taskRepository;
    @Autowired
    IUserRepository userRepository;

    @PostMapping("/create") // Sei la pq isso so ta funcionando com @RequestBody(required = false)
    public ResponseEntity createTask(@RequestBody(required = false) TaskModel task, HttpServletRequest request){
        try {
            UserModel user = userRepository.findByuserName(request.getAttribute("userName").toString());
            task.setUser(user);
            TaskModel createdTask = taskRepository.save(task);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public ResponseEntity getUserTasks(HttpServletRequest request){
        System.out.println("DUPLICADO");
        try {
            UserModel user = userRepository.findByuserName(request.getAttribute("userName").toString());
            List<TaskModel> tasks = taskRepository.findByUser(user);
            System.out.println(tasks);
            return  ResponseEntity.status(HttpStatus.OK).body(tasks);
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
