package it.klotski.web.game.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/csrf")
public class CsrfController {

    @GetMapping
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
