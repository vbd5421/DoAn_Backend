package com.doan.backend.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.*;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({ResourceException.class})
    public ResponseEntity<String> handleResourceNotFoundException(ResourceException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> handleIOEException(IOException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy file");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<String> messError = new ArrayList<>();
        String error = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }







    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handleDuplicateKeyException(DuplicateKeyException e) {
        // Xử lý ngoại lệ DuplicateKeyException và trả về một ResponseEntity với mã lỗi và thông báo
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Tên đã tồn tại.");
    }
}

