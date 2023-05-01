package it.klotski.web.game.handler;

import it.klotski.web.game.exceptions.UserAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyPresentException.class)
    public ResponseEntity<?> userAlreadyRegisteredException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already registered");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body("Wrong username or password");
    }
}
