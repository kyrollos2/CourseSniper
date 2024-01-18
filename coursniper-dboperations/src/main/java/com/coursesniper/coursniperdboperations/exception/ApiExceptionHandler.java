package com.coursesniper.coursniperdboperations.exception;

import java.nio.file.AccessDeniedException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;


/*The purpose of this class is to define how different types of errors 
are  displayed and handled on  the client  side.*/
@ControllerAdvice
public class ApiExceptionHandler {

    // Handle custom ApiRequestException any errors that happen in the business logic
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        ApiErrorResponsePacket errorResponse = new ApiErrorResponsePacket(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(),
                Collections.emptyList()); 
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //Handle Runtime errors that occur unexpectedely
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        ApiErrorResponsePacket errorResponse = new ApiErrorResponsePacket(
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(),
                Collections.emptyList()); //empty list to fulfill constructor
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This handles if the desired entity is not located
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        ApiErrorResponsePacket errorResponse = new ApiErrorResponsePacket(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(),
                Collections.emptyList());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /*  This handles an improperly formatted request.
     * it will harvest a full list of the mistakes
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                               .getAllErrors()
                               .stream() //handle collecting of the error objects
                               .map(ObjectError::getDefaultMessage) //transforms each object into readable format
                               .collect(Collectors.toList()); //Stream of mistakes collected

        ApiErrorResponsePacket errorResponse = new ApiErrorResponsePacket( //Create new response packet
                "Validation errors occurred",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(),
                errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*  Handle access denied exceptions, useful for 
     * situations where both user or system are unable to access resource
    */
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        ApiErrorResponsePacket errorResponse = new ApiErrorResponsePacket(
                "Access denied",
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(),
                Collections.emptyList());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /*This handles any breakdown in database access */
    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<Object> handleDataAccessException(DataAccessException e) {
        ApiErrorResponsePacket errorResponse = new ApiErrorResponsePacket(
                "Database access error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(),
                Collections.emptyList()); // Empty error list
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /* This handles situations when an HTTP request sends a method
     * that is not authorized for the specified server
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ApiErrorResponsePacket errorResponse = new ApiErrorResponsePacket(
                "Method not supported",
                HttpStatus.METHOD_NOT_ALLOWED,
                ZonedDateTime.now(),
                Collections.emptyList()); // Empty error list
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    /* Handles a mutated or malformed Json request */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ApiErrorResponsePacket errorResponse = new ApiErrorResponsePacket(
                "Malformed JSON request",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(),
                Collections.emptyList()); // Empty error list
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    

}
