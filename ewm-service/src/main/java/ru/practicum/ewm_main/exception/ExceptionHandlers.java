package ru.practicum.ewm_main.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final DataIntegrityViolationException e) {
        return new ApiError.ApiErrorBuilder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("The required object was found.")
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleUserNotFoundException(final NotFoundException e, WebRequest request) {
        return new ApiError.ApiErrorBuilder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Object not found " + request.getDescription(false))
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleValidationException(final BadRequestException e, WebRequest request) {
        return new ApiError.ApiErrorBuilder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason(request.getDescription(false))
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternalServerErrorException(final HttpServerErrorException.InternalServerError e, WebRequest request) {
        return new ApiError.ApiErrorBuilder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason(request.getDescription(false))
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleThrowableExceptions(final Throwable e) {
        return new ApiError.ApiErrorBuilder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Throwable exception")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
