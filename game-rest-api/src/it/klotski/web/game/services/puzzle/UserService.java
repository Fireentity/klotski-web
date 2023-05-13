package it.klotski.web.game.services.puzzle;

import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.UserAlreadyPresentException;
import it.klotski.web.game.payload.requests.RegisterRequest;
import it.klotski.web.game.repositories.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Service per gestire la ricerca o l'immissione di utenti all'interno del database.
 */
@Service
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Costruttore di classe:
     * @param userRepository è la tabella degli utenti.
     * @param passwordEncoder è l'oggetto per crittografare tramite funzione di hash.
     */
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Funzione per cercare un utente nel database in base all'email.
     * @param email stringa contenente l'email da cercare all'interno del database.
     * @return l'user, se esiste, associato alla email data in input.
     * @throws UsernameNotFoundException nel caso di utente con tale email non presente.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> //funzione che ritorna se presente il valore cercato, sennò lancia una data eccezione.
                        new UsernameNotFoundException("User not found with username or email: " + email));
    }

    /**
     * Funzione per creare un nuovo utente e salvarlo all'interno del database.
     * Il campo password viene crittografato usando una funzione di hash prima di essere salvato nel database.
     * @param registerRequest oggetto contenente un campo email e un campo password, che andranno a comporre i rispettivi campi di User.
     * @throws UserAlreadyPresentException nel caso di utente già registrato.
     */
    public void createUser(RegisterRequest registerRequest) {
        if(userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyPresentException();
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);
    }

}
