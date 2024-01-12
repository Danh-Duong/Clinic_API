package com.example.Clinic_API.exception;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception e, WebRequest request){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setResponseCode(ResponseCode.ERROR.getCode());
        errorResponse.setResponseStatus(ResponseCode.ERROR.name());
        errorResponse.setTimestamp(new Date());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setDetail(request.getDescription(false));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setResponseCode(ResponseCode.ERROR.getCode());
        errorResponse.setResponseStatus(ResponseCode.ERROR.name());
        errorResponse.setTimestamp(new Date());
        Map<String,String> errors=new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
        errorResponse.setMessage(errors.toString());
        errorResponse.setDetail(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
