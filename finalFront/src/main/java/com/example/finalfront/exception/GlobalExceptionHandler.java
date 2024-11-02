package com.example.finalfront.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(RuntimeException ex) {
        ModelAndView modelAndView = new ModelAndView("error/access-denied");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }
}
