package com.springboot.blog.exception;

import com.springboot.blog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

//Controller advice is used to handle the exception globally.@ControllerAdvice ANNOTATION IS HAVING @Component annotation too which itself creates the bean
//ApplicationContext is where spring holds the instances of all the objects that is has identified to be managed and distribute it automatically
//Using the IOC method spring collects all the beans from our application and return it whenever it is required in the application at the run time
//@Component annotation allow spring to automatically detect our custom beans .Spring scan our classes annotated with @Component
@ControllerAdvice
public class GlobalExceptionHandler {

    //handle specific exception and send it back to the client

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
