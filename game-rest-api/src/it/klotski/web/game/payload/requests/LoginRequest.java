package it.klotski.web.game.payload.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe per la gestione delle richieste di login di un utente.
 *
 * La classe `LoginRequest` è una classe di richiesta utilizzata per gestire le richieste di login di un utente.
 * Contiene i seguenti campi:
 * - `email`: l'indirizzo email dell'utente di tipo `String`
 * - `password`: la password dell'utente di tipo `String`
 *
 * La classe è annotata con `@Getter`, che genera automaticamente i metodi getter per i campi, consentendo di accedere al valore dell'attributo.
 * È anche annotata con `@RequiredArgsConstructor`, che genera automaticamente un costruttore con i campi richiesti come argomenti, semplificando così la creazione di un oggetto `LoginRequest` con i valori desiderati per l'email e la password.
 */
@Getter
@RequiredArgsConstructor
public class LoginRequest {
    private final String email;
    private final String password;
}
