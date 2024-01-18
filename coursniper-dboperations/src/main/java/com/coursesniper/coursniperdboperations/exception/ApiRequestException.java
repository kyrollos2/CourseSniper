package com.coursesniper.coursniperdboperations.exception;

/*This class is an exception object that can be
 * use throught out the application. This Class 
 * in combination with the Exception handler class
 * allows for strategically handling a variety of errors
 * that occur at runtime
  */
    public class ApiRequestException extends RuntimeException {
        public ApiRequestException(String message) {
            super(message);
        }
    
        public ApiRequestException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    

