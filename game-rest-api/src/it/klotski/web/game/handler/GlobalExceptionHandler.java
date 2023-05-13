package it.klotski.web.game.handler;

import it.klotski.web.game.exceptions.ConfigurationNotFoundException;
import it.klotski.web.game.exceptions.InvalidBoardConfigurationException;
import it.klotski.web.game.exceptions.UserAlreadyPresentException;
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
     * Funzione per la gestione dell'eccezione UserAlreadyPresentException.
     * @return alla pagina lo stato CONFLICT e il messaggio di errore "User already registered".
     */
    @ExceptionHandler(UserAlreadyPresentException.class)
    public ResponseEntity<?> userAlreadyRegisteredException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already registered");
    }

    /**
     * Funzione per la gestione dell'eccezione BadCredentialsException.
     * @return alla pagina lo stato UNAUTHORIZED e il messaggio di errore "Wrong username or password".
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body("Wrong username or password");
    }

    /**
     * Funzione per la gestione dell'eccezione ConfigurationNotFoundException.
     * @return alla pagina lo stato BAD_REQUEST e il messaggio di errore "Unable to find start configuration for that id".
     */
    @ExceptionHandler(ConfigurationNotFoundException.class)
    public ResponseEntity<?> configurationNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Unable to find start configuration for that id");
    }

    /**
     * Funzione per la gestione dell'eccezione InvalidBoardConfigurationException.
     * @return alla pagina lo stato BAD_REQUEST e il messaggio di errore "Invalid board configuration".
     */
    @ExceptionHandler(InvalidBoardConfigurationException.class)
    public ResponseEntity<?> invalidBoardConfigurationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Invalid board configuration");
    }
}
