package com.utn.repasoFinal.exception;

import javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseExeptionHandler extends ResponseEntityExceptionHandler {

    //Validation Models
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handlerContraintViolation(ConstraintViolationException ex , WebRequest request){
        List<String> errors = new ArrayList<>();

        for (ConstraintViolation violation : ex.getConstraintViolations())
            errors.add( "Route:  "+violation.getRootBeanClass()+" || Message:  "+violation.getMessage()  );

        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage() ,errors);

        return new ResponseEntity<Object>(apiError ,new HttpHeaders(),apiError.getHttpStatus());
    }

    //NotFound
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handlerContraintViolation(NotFoundException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("Message:  "+ex.getMessage());

        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage() ,errors);

        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //NotFound
    @ExceptionHandler({SizeLimitExceededException.class})
    public ResponseEntity<Object> handlerContraintViolation(SizeLimitExceededException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("Message:  "+ex.getMessage());

        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage() ,errors);

        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //SQL
    @ExceptionHandler({java.sql.SQLException.class})
    public ResponseEntity<Object> handlerContraintViolation(java.sql.SQLException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("SQL: "+ex.getSQLState()  );
        errors.add("Message: "+ex.getMessage());

        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage() ,errors);

        return  ResponseEntity.status(apiError.getHttpStatus()).header("Constraint","UNIQUE Name").body(apiError);
    }

    //HTTP
    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Object> handlerContraintViolation(HttpClientErrorException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("Message:  "+ex.getMessage());
        ApiError apiError= new ApiError(ex.getStatusCode(),ex.getLocalizedMessage() ,errors);

        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }
}
