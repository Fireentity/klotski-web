package it.klotski.web.game.payload.reponses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

/**
 * Questa classe rappresenta la risposta del token CSRF.
 *
 * La classe CsrfTokenResponse contiene le informazioni relative al token CSRF,
 * come il nome del parametro, il token stesso e il nome dell'header.
 */
public class CsrfTokenResponse {
    /**
     * Il nome del parametro del token CSRF.
     */
    private final String parameterName;

    /**
     * Il valore del token CSRF.
     */
    private final String token;

    /**
     * Il nome dell'header del token CSRF.
     */
    private final String headerName;
}
