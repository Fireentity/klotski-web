package it.klotski.web.game.controllers;

import it.klotski.web.game.payload.reponses.CsrfTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Il controller che gestisce le richieste relative al token CSRF.
 * Fornisce un endpoint per ottenere il token CSRF corrente.
 */

@RestController
@RequestMapping("/api/csrf")
public class CsrfController {

    /**
     * Restituisce un oggetto CsrfTokenResponse contenente i dettagli del token CSRF.
     *
     * @param request l'oggetto HttpServletRequest
     * @return un oggetto CsrfTokenResponse contenente il nome del parametro CSRF, il token CSRF e il nome dell'header CSRF
     */
    @GetMapping
    public CsrfTokenResponse getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return new CsrfTokenResponse(csrfToken.getParameterName(), csrfToken.getToken(), csrfToken.getHeaderName());
    }

}
