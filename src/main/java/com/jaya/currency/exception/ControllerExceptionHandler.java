package com.jaya.currency.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleClientNotFoundException(
            ClientNotFoundException ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorMessage> handleClientException(
            ClientException ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(
                ex.getStatus().value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<ErrorMessage>(message, ex.getStatus());
    }

    @ExceptionHandler(CurrencyException.class)
    public ResponseEntity<ErrorMessage> handleClientException(
            CurrencyException ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(
                ex.getStatus().value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<ErrorMessage>(message, ex.getStatus());
    }

    @ExceptionHandler(ExchangeRatesApiException.class)
    public ResponseEntity<ErrorMessage> handleExchangeRatesApiException(
            ClientException ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(
                ex.getStatus().value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<ErrorMessage>(message, ex.getStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
