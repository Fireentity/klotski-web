package it.klotski.web.game.payload.requests;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe per la gestione delle richieste di login di un utente.
 */
@Getter
@RequiredArgsConstructor
public class LoginRequest {
    private final String email;
    private final String password;
}
