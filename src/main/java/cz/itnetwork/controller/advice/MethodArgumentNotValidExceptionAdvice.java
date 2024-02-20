package cz.itnetwork.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MethodArgumentNotValidExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("status", "400");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors())
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        return ResponseEntity.ok().body(errorsMap);
    }
}
