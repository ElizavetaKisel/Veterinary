package by.overone.veterinary.controller.exception;

import by.overone.veterinary.service.exception.EntityAlreadyExistException;
import by.overone.veterinary.service.exception.EntityNotFoundException;
import by.overone.veterinary.service.exception.MyValidationException;
import by.overone.veterinary.service.exception.UpdateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        String message = messageSource.getMessage("00000", null, request.getLocale());
        response.setMessage(message);
        log.info("No handler found", ex);
        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        String message = messageSource.getMessage("00001", null, request.getLocale());
        response.setMessage(message);
        log.info("Not readable ", ex);
        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        String message = messageSource.getMessage("00002", null, request.getLocale());
        response.setMessage(message);
        log.info("Method not allowed ", ex);
        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<ExceptionResponse> list = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ExceptionResponse(error.getField() + " " + error.getDefaultMessage(),
                        null, null))
                .collect(Collectors.toList());
        log.info("Validation error", ex);
        return new ResponseEntity<>(list, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintHandle(ConstraintViolationException ex) {
        List<ExceptionResponse> list = ex.getConstraintViolations()
                .stream()
                .map(error -> new ExceptionResponse(error.getMessage(),
                        null, null))
                .collect(Collectors.toList());
        log.info("Validation error", ex);
        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        String message = messageSource.getMessage("33331", null, request.getLocale());
        response.setMessage(message);
        log.info("Wrong type: ", ex);
        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        String message = messageSource.getMessage("55555", null, request.getLocale());
        response.setMessage(message);
        log.info("Missing parameter: ", ex);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> entityNotFoundHandler(EntityNotFoundException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode(e.getCode().getErrorCode());
        String message = messageSource.getMessage(e.getCode().getErrorCode(), null, request.getLocale());
        response.setMessage(message);
        log.info("Not found exception: ", e);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(SQLException ex) {
        log.error("SQL exception: ", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        response.setErrorCode("12345");
        response.setMessage("SQL exception");
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> entityAlreadyExistHandler(EntityAlreadyExistException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode(e.getCode().getErrorCode());
        String message = messageSource.getMessage(e.getCode().getErrorCode(), null, request.getLocale());
        response.setMessage(message);
        log.info("Already exist exception: ", e);
        return new ResponseEntity<>(response, HttpStatus.LOCKED);
    }

    @ExceptionHandler(MyValidationException.class)
    public ResponseEntity<ExceptionResponse> wrongDateTimeHandler(MyValidationException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode(e.getCode().getErrorCode());
        String message = messageSource.getMessage(e.getCode().getErrorCode(), null, request.getLocale());
        response.setMessage(message);
        log.info("My valid exception: ", e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UpdateException.class)
    public ResponseEntity<ExceptionResponse> wrongDateTimeHandler(UpdateException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode(e.getCode().getErrorCode());
        String message = messageSource.getMessage(e.getCode().getErrorCode(), null, request.getLocale());
        response.setMessage(message);
        log.info("Update exception: ", e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
