package fr.cyu.rt.control.api.rest.exception_handler;

import fr.cyu.rt.control.api.rest.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

/**
 * The base handler for all exceptions thrown be the api calls.
 * <p>
 * Note : each controller method MUST add the {@code throws Exception} in the signature in order
 * to be registered and handled by this controller advice
 *
 * @author Aldric Vitali Silvestre
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, WebRequest request) {
        return createErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoSuchElementException exception, WebRequest request) {
        return createErrorResponse(request, HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleDeniedException(AccessDeniedException exception, WebRequest request) {
        return createErrorResponse(request, HttpStatus.UNAUTHORIZED, exception);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgException(IllegalArgumentException exception,
                                                                   WebRequest request) {
        return createErrorResponse(request, HttpStatus.BAD_REQUEST, exception);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(WebRequest request,
                                                              HttpStatus status,
                                                              Exception exception) {
        String path = "";
        if (request instanceof ServletWebRequest servletRequest) {
            path = servletRequest.getRequest().getRequestURI().toString();
        }

        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(path, status, exception));
    }
}
