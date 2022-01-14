package by.overone.veterinary.controller.exception;

import by.overone.veterinary.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

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
}
