package it.klotski.web.game.exceptions.handler;

import it.klotski.web.game.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Classe per la gestione delle eccezioni.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestisce l'eccezione UserAlreadyPresentException.
     *
     * @return Una ResponseEntity con lo stato CONFLICT e il messaggio di errore "User already registered".
     */
    @ExceptionHandler(UserAlreadyPresentException.class)
    public ResponseEntity<?> handleUserAlreadyRegisteredException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already registered");
    }

    /**
     * Gestisce l'eccezione BadCredentialsException.
     *
     * @return Una ResponseEntity con lo stato UNAUTHORIZED e il messaggio di errore "Wrong username or password".
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body("Wrong username or password");
    }

    /**
     * Gestisce l'eccezione ConfigurationNotFoundException.
     *
     * @return Una ResponseEntity con lo stato BAD_REQUEST e il messaggio di errore "Unable to find start configuration for that id".
     */
    @ExceptionHandler(ConfigurationNotFoundException.class)
    public ResponseEntity<?> handleConfigurationNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Unable to find start configuration for that id");
    }

    /**
     * Gestisce l'eccezione InvalidBoardConfigurationException.
     *
     * @return Una ResponseEntity con lo stato BAD_REQUEST e il messaggio di errore "Invalid board configuration".
     */
    @ExceptionHandler(InvalidBoardConfigurationException.class)
    public ResponseEntity<?> handleInvalidBoardConfigurationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Invalid board configuration");
    }

    /**
     * Gestisce l'eccezione MoveNotFoundException.
     *
     * @return Una ResponseEntity con lo stato CONFLICT e il messaggio di errore "There are no moves in this game".
     */
    @ExceptionHandler(MoveNotFoundException.class)
    public ResponseEntity<?> handleMoveNotFoundException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("There are no moves in this game");
    }

    /**
     * Gestisce l'eccezione SolutionNotFoundException.
     *
     * @return Una ResponseEntity con lo stato NOT_FOUND e il messaggio di errore "Unable to calculate next best move".
     */
    @ExceptionHandler(SolutionNotFoundException.class)
    public ResponseEntity<?> handleSolutionNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to calculate next best move");
    }

    /**
     * Gestisce l'eccezione GameAlreadyFinishedException.
     *
     * @return Una ResponseEntity con lo stato CONFLICT e il messaggio di errore "This game is already finished".
     */
    @ExceptionHandler(GameAlreadyFinishedException.class)
    public ResponseEntity<?> handleGameAlreadyFinishedException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("This game is already finished");
    }

    /**
     * Gestisce l'eccezione GameAlreadyStartedException.
     *
     * @return Una ResponseEntity con lo stato CONFLICT e il messaggio di errore "This game is already started".
     */
    @ExceptionHandler(GameAlreadyStartedException.class)
    public ResponseEntity<?> handleGameAlreadyStartedException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("This game is already started");
    }
}
