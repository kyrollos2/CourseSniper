package com.coursesniper.coursniperdboperations.exception;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*This class defines an error message object that is sent 
upon exception*/
@Data
@AllArgsConstructor
@NoArgsConstructor
//All fields are final because this a data transfer object
public class ApiErrorResponsePacket {
    private  String message; // User-friendly error message
    private  HttpStatus httpStatus; // HTTP status code
    private ZonedDateTime timestamp; // Time of the exception
    private List<String> errors; // List of validation errors

    
}
