package com.bank.manager.exception;

import com.bank.manager.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles AccountNotFoundException by returning a 404 Not Found response.
     *
     * @param ex The caught AccountNotFoundException
     * @param request The HTTP request that resulted in the exception
     * @return ResponseEntity containing error details with HTTP 404 status
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex,
                                                               HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles InsufficientBalanceException by returning a 422 Unprocessable Entity response.
     *
     * @param ex The caught InsufficientBalanceException
     * @param request The HTTP request that resulted in the exception
     * @return ResponseEntity containing error details with HTTP 422 status
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex,
                                                                   HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    /**
     * Handles validation errors from @Valid annotations by returning a 400 Bad Request response.
     * Collects all field validation errors into a single error message.
     *
     * @param ex The caught MethodArgumentNotValidException
     * @param request The HTTP request that resulted in the validation error
     * @return ResponseEntity containing validation error details with HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Handles IllegalArgumentException by returning a 400 Bad Request response.
     * Used for general argument validation failures.
     *
     * @param ex The caught IllegalArgumentException
     * @param request The HTTP request that resulted in the exception
     * @return ResponseEntity containing error details with HTTP 400 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex,
                                                               HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles InvalidAmountException by returning a 400 Bad Request response.
     * Used when an invalid monetary amount is provided in a request.
     *
     * @param ex The caught InvalidAmountException
     * @param request The HTTP request that contained the invalid amount
     * @return ResponseEntity containing error details with HTTP 400 status
     */
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAmount(InvalidAmountException ex,
                                                           HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Global exception handler that catches all unhandled exceptions.
     * Returns a 500 Internal Server Error response.
     * In a production environment, consider logging the full stack trace.
     *
     * @param ex The caught Exception
     * @param request The HTTP request that resulted in the exception
     * @return ResponseEntity with a generic error message and HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex,
                                                       HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Unexpected error occurred",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // all private methods below
    /**
     * Helper method to build a consistent error response.
     *
     * @param ex The exception that was thrown
     * @param status The HTTP status code to return
     * @param request The HTTP request that caused the error
     * @return ResponseEntity containing the error details
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex,
                                                             HttpStatus status,
                                                             HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }

}