package it.klotski.web.game.repositories;

import it.klotski.web.game.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository per l'accesso ai dati degli utenti.
 *
 * L'interfaccia `IUserRepository` estende l'interfaccia `JpaRepository` di Spring Data JPA per fornire
 * operazioni di accesso ai dati degli utenti.
 *
 * Viene utilizzato l'annotazione `@Repository` per indicare che questa interfaccia è un componente di
 * repository gestito da Spring.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    /**
     * Trova un utente tramite l'indirizzo email.
     *
     * @param email l'indirizzo email dell'utente da cercare
     * @return un'istanza di `Optional<User>` contenente l'utente trovato (se presente), altrimenti un'istanza vuota
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica l'esistenza di un utente tramite l'indirizzo email.
     *
     * @param email l'indirizzo email dell'utente da verificare
     * @return `true` se l'utente esiste, `false` altrimenti
     */
    boolean existsUserByEmail(String email);
}
