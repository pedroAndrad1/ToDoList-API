package com.pedroandrad.ToDoList.task;

import com.pedroandrad.ToDoList.user.IUserRepository;
import com.pedroandrad.ToDoList.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    ITaskRepository taskRepository;
    @Autowired
    IUserRepository userRepository;

    @PostMapping("/create") // Sei la pq isso so ta funcionando com @RequestBody(required = false)
    public ResponseEntity createTask(@RequestBody(required = false) TaskModel task, HttpServletRequest request){
        System.out.println(task);
        try {
            UserModel user = userRepository.findByuserName(request.getAttribute("userName").toString());
            task.setUser(user);
            TaskModel createdTask = taskRepository.save(task);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
