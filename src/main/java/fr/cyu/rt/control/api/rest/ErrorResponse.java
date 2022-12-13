package fr.cyu.rt.control.api.rest;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * The global error response for all api calls. Can be created by hand,
 * but most of the time it will be created by the
 * {@link fr.cyu.rt.test_websockets.api.rest.exception_handler.ControllerExceptionHandler}
 * when an exception is thrown
 *
 * @author Aldric Vitali Silvestre
 */
public record ErrorResponse(
        String path,
        LocalDateTime timestamp,
        int status,
        String statusLabel,
        String message,
        String exception,
        String exceptionMessage
) {

    public ErrorResponse(String path, HttpStatus status, Exception exception) {
        this(path,
             LocalDateTime.now(),
             status.value(),
             status.name(),
             status.getReasonPhrase(),
             exception.getClass().getName(),
             exception.getMessage()
        );
    }

}
