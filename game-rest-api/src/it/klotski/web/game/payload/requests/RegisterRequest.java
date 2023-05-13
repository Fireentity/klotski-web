package it.klotski.web.game.payload.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe per la gestione delle richieste di creazione di un nuovo utente (registrazione).
 */
@Getter
@RequiredArgsConstructor
public class RegisterRequest {
    private final String email;
    private final String password;
}
