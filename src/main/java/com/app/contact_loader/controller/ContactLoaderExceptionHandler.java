package com.app.contact_loader.controller;

import com.app.contact_loader.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ContactLoaderExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String, Object> handleRequestException(Exception ex) {
        log.error(ex.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("error", "Request Error");
        map.put("cause", ex.getMessage());
        return map;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody Map<String, Object> handleUncaughtException(Exception ex) throws IOException {
        log.error(ex.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("error", "Unknown Error");
        if (ex.getCause() != null) {
            map.put("cause", ex.getCause().getMessage());
        } else {
            map.put("cause", ex.getMessage());
        }
        return map;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody Map<String, Object> handleNotFoundException(NotFoundException ex) throws IOException {
        log.error(ex.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("error", "There is no filter matches.");
        map.put("cause", ex.getCause().getCause().getLocalizedMessage());
        return map;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody Map<String, Object> handleSQLException(RuntimeException ex) throws IOException {
        log.error(ex.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("error", "Database error.");
        map.put("cause", ex.getCause().getCause().getLocalizedMessage());
        return map;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody Map<String, Object> nullPointerException(RuntimeException ex) throws IOException {
        log.error(ex.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("error", "Sorry, Something Went Wrong: Please try again later.");
        map.put("cause", ex.getCause().getCause().getLocalizedMessage());
        return map;
    }
}