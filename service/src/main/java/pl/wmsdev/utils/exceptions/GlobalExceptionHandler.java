package pl.wmsdev.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoActiveProgrammeException.class)
    public ResponseEntity<Exception> handleNoActiveProgrammeException(NoActiveProgrammeException ex) {
        return handleException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity<Exception> handleException(HttpStatus status, String exceptionMessage) {
        return ResponseEntity
                .status(status)
                .body(new Exception(exceptionMessage));
    }
}

