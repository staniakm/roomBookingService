package com.room.booking.controller;

import com.room.booking.exception.ApiErrorResponse;
import com.room.booking.exception.CancelOtherCustomerBookingException;
import com.room.booking.exception.EntityNotExistsException;
import com.room.booking.exception.RoomAlreadyBookedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EntityNotExistsException.class, CancelOtherCustomerBookingException.class, RoomAlreadyBookedException.class})
    public ResponseEntity<ApiErrorResponse> entityNotExists(Exception ex, WebRequest request) {

        return prepareResponse(
                new ApiErrorResponse.ApiErrorResponseBuilder()
                        .withStatus(HttpStatus.BAD_REQUEST)
                        .withErrorCode(HttpStatus.BAD_REQUEST.toString())
                        .withMessage(ex.getMessage())
                        .withDetail(request.getDescription(false)).build());

    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiErrorResponse> handleValidationException(ConstraintViolationException ex, WebRequest request) {

        StringBuilder errorMessage = new StringBuilder();
        ex.getConstraintViolations().forEach(exception -> errorMessage.append(exception.getPropertyPath()).append(" - ").append(exception.getMessage()));
        return prepareResponse(
                new ApiErrorResponse.ApiErrorResponseBuilder()
                        .withStatus(HttpStatus.BAD_REQUEST)
                        .withErrorCode(HttpStatus.BAD_REQUEST.toString())
                        .withMessage(errorMessage.toString())
                        .withDetail(request.getDescription(false)).build());

    }

    private ResponseEntity<ApiErrorResponse> prepareResponse(ApiErrorResponse apiErrorResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return new ResponseEntity<>(apiErrorResponse, headers, apiErrorResponse.getStatus());
    }
}
