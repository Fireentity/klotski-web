package it.klotski.web.game.payload.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe per la gestione delle richieste di creazione di un nuovo utente (registrazione).
 *
 * La classe `RegisterRequest` è una classe di richiesta che contiene i campi necessari per la creazione di un nuovo utente.
 * È annotata con `@Getter` per generare automaticamente i metodi getter per i campi e con `@RequiredArgsConstructor`
 * per generare automaticamente un costruttore con tutti i campi richiesti come argomenti.
 */
@Getter
@RequiredArgsConstructor
public class RegisterRequest {
    private final String email;
    private final String password;
}
