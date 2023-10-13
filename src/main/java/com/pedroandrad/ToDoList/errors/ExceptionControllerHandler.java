package com.pedroandrad.ToDoList.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadbleException(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body(e.getMostSpecificCause().getMessage());
    }
}
