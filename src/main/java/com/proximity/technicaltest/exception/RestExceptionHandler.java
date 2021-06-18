package com.proximity.technicaltest.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {
  protected final Log logger = LogFactory.getLog(RestExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity handleNotFound(final NotFoundException ex, final WebRequest request) {
    logger.error("Unable to find the aggregate or entity: ", ex);
    return  ResponseEntity.notFound().build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
    final Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return errors;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public Map<String, String> handleIllegalArgumentException(final IllegalArgumentException ex) {
    final Map<String, String> errors = new HashMap<>();
    errors.put("Internal Error:", ex.getMessage());
    return errors;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public Map<String, String> UnhandledException(final Exception ex) {
    logger.error("Exception occurred: ", ex);
    final Map<String, String> errors = new HashMap<>();
    errors.put("Internal Error: ", ex.getMessage());
    return errors;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadCredentialsException.class)
  public Map<String, String> handle(final BadCredentialsException ex) {
    final Map<String, String> errors = new HashMap<>();
    errors.put("Authentication Failed:", "Username or Password is incorrect");
    return errors;
  }


}
