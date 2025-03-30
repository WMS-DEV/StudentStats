package pl.wmsdev.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import pl.wmsdev.utils.exceptions.NotFoundException;
import pl.wmsdev.utils.dtos.ProblemDetailsDTO;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetailsDTO> handleNoActiveProgrammeException(NotFoundException exception) {
        return handleException(
                exception,
                HttpStatus.NOT_FOUND,
                new ProblemDetailsDTO(exception.getMessage()));
    }

    private <T extends Exception> ResponseEntity<ProblemDetailsDTO> handleException(T exception, HttpStatus httpStatus, ProblemDetailsDTO problemDetails) {
        logException(exception, problemDetails);

        return ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problemDetails);
    }

    private <T extends Exception> void logException(T exception, ProblemDetailsDTO problemDetails) {
        log.error(problemDetails.message(), exception);
    }
}

