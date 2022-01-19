package by.overone.veterinary.controller.exception;

import by.overone.veterinary.exception.EntityNotFoundException;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> entityNotFoundHandler(EntityNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode(e.getCode().getErrorCode());
        String message = "";
        switch (e.getCode().getErrorCode()) {
            case "0001": message = "User not found";
            break;
            case "0002": message = "Pet not found";
            break;
        }
        response.setMessage(message);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(SQLException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode("5000");
        response.setMessage("Bad SQL");
        log.info("SQL EXCEPTION: ", e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionResponse> entityAlreadyExistHandler(DuplicateKeyException e){
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode("6000");
        response.setMessage("Entity already exist");
        log.info("ALREADY EXIST EXCEPTION: ", e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<ExceptionResponse> validationHandler(ValidationException e) {
//        ExceptionResponse response = new ExceptionResponse();
//        response.setException(e.getClass().getSimpleName());
//        response.setErrorCode("7000");
//        response.setMessage(e.getMessage());
//        log.info("VALIDATION EXCEPTION: ", e);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
}
