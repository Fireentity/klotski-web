package it.klotski.web.game.controllers;

import it.klotski.web.game.payload.reponses.CsrfTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/csrf")
public class CsrfController {

    @GetMapping
    public CsrfTokenResponse getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return new CsrfTokenResponse(csrfToken.getParameterName(), csrfToken.getToken(), csrfToken.getHeaderName());
    }

}