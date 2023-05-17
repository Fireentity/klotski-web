package it.klotski.web.game.controllers;

import it.klotski.web.game.payload.requests.LoginRequest;
import it.klotski.web.game.payload.requests.RegisterRequest;
import it.klotski.web.game.services.puzzle.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * La classe AuthController gestisce le operazioni di autenticazione e registrazione degli utenti.
 * Fornisce endpoint per il login e la registrazione.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    /**
     * Il servizio utente utilizzato per gestire le operazioni legate agli utenti.
     */
    private final UserService userService;

    /**
     * Il gestore di autenticazione utilizzato per autenticare gli utenti.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Il repository del contesto di sicurezza utilizzato per gestire il contesto di sicurezza delle richieste.
     */
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    /**
     * La strategia di gestione del contesto di sicurezza utilizzata per recuperare e impostare il contesto di sicurezza.
     */
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    /**
     * Costruisce un'istanza di AuthController con i servizi utente e il gestore di autenticazione specificati.
     *
     * @param userService             il servizio utente utilizzato per gestire le operazioni legate agli utenti
     * @param authenticationManager   il gestore di autenticazione utilizzato per autenticare gli utenti
     */
    @Autowired
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Metodo per autenticare un utente.
     *
     * @param loginRequest la richiesta di login contenente email e password dell'utente
     * @param request      l'oggetto HttpServletRequest
     * @param response     l'oggetto HttpServletResponse
     * @return una ResponseEntity contenente un messaggio di successo in caso di autenticazione avvenuta con successo
     */
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        return new ResponseEntity<>("L'utente si è autenticato con successo.", HttpStatus.OK);
    }

    /**
     * Metodo per registrare un nuovo utente.
     *
     * @param registerRequest la richiesta di registrazione contenente i dati dell'utente
     * @return una ResponseEntity contenente un messaggio di successo in caso di registrazione avvenuta con successo
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        userService.createUser(registerRequest);
        return ResponseEntity.ok("Utente registrato con successo");
    }
}
