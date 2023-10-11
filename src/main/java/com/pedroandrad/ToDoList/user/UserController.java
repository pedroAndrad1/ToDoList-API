package com.pedroandrad.ToDoList.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository repository;
    @PostMapping()
    public ResponseEntity createUser(@RequestBody UserModel user){
        try {
           UserModel createdUser = this.repository.save(user);
           return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
        catch (DataIntegrityViolationException err){
            System.out.println(err);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
