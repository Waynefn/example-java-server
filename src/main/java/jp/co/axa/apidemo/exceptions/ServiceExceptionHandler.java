package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice

public class ServiceExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
        System.out.println("Unhandled Exception Happened");
        return new ResponseEntity<>("An internal error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        System.out.println("Illegal Argument Exception Happened");
        return new ResponseEntity<>("Illegal Argument: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // We can define specific exception for this. Here is just an example.
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(RecordNotFoundException ex, WebRequest request) {
        System.out.println("Employee ID not found: " + ex.getMessage());
        return new ResponseEntity<>("Employee ID not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
