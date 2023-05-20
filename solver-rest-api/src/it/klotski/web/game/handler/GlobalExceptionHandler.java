package it.klotski.web.game.handler;


import it.klotski.web.game.exceptions.SolutionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SolutionNotFoundException.class)
    public ResponseEntity<?> solutionNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to calculate next best move");
    }
}
