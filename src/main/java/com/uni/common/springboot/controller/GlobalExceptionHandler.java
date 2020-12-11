package com.uni.common.springboot.controller;

import com.uni.common.springboot.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public <T> ResponseEntity<UniApiResponse<T>> handleValidationException(ValidationException exception) {
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;

            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations ();
            if (!ObjectUtils.isEmpty (violations)) {
                ConstraintViolation<?> next = violations.iterator ().next ();
                return new ResponseEntity<>(UniApiResponse.createErrorResponse(next.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(UniApiResponse.createErrorResponse("参数异常..."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ResponseEntity<UniApiResponse<T>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult ();
        if (bindingResult.hasErrors ()) {
            return new ResponseEntity<>(UniApiResponse.createErrorResponse(bindingResult.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UniApiResponse.createErrorResponse("参数异常..."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObtainTokenFailedException.class)
    public <T> ResponseEntity<UniApiResponse<T>> handleObtainTokenFailedException(ObtainTokenFailedException ex) {
        return new ResponseEntity<>(UniApiResponse.createErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtParseException.class)
    public <T> ResponseEntity<UniApiResponse<T>> handleJwtParseException(JwtParseException ex) {
        return new ResponseEntity<>(UniApiResponse.createErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public <T> ResponseEntity<UniApiResponse<T>> handleAccessDeniedException(AccessDeniedException e){
        return new ResponseEntity<>(UniApiResponse.createErrorResponse(MessageUtil.getText("error.AccessDenied")), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UniNotFoundException.class)
    public <T> ResponseEntity<UniApiResponse<T>> handleNotFound(Exception ex) {
        UniNotFoundException notFound = (UniNotFoundException) ex;

        return new ResponseEntity<>(UniApiResponse.createErrorResponse(notFound.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UniRuntimeException.class)
    public <T> ResponseEntity<UniApiResponse<T>> handleUniRuntime(Exception ex) {
        UniRuntimeException exception = (UniRuntimeException) ex;

        return new ResponseEntity<>(UniApiResponse.createErrorResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<UniApiResponse<T>> handleException(Exception e) {
        log.error (e.getMessage (), e);
        return new ResponseEntity<>(UniApiResponse.createErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
