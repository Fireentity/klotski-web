package it.klotski.web.game.repositories;

import it.klotski.web.game.domain.game.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository per l'accesso ai dati delle partite.
 * L'interfaccia `IGameRepository` estende l'interfaccia `PagingAndSortingRepository` e `CrudRepository` di Spring Data
 * per fornire operazioni di accesso ai dati delle partite.
 * Viene utilizzata l'annotazione `@Repository` per indicare che questa interfaccia è un componente di repository gestito da Spring.
 */
@Repository
public interface IGameRepository extends PagingAndSortingRepository<Game, Long>, CrudRepository<Game, Long> {

    /**
     * Trova tutte le partite di un giocatore, identificato dall'email, con paginazione.
     *
     * @param email    l'email del giocatore
     * @param pageable le informazioni di paginazione
     * @return una lista di oggetti `Game` corrispondenti alle partite trovate
     */
    List<Game> findAllByPlayer_EmailOrderByCreatedAtDesc(String email, Pageable pageable);

    /**
     * Trova una partita per ID.
     *
     * @param id l'ID della partita da trovare
     * @return un'istanza di `Optional<Game>` contenente la partita trovata (se presente), altrimenti un'istanza vuota
     */
    Optional<Game> findGameById(long id);

    /**
     * Trova la prima partita di un giocatore che sia finita, ordinata per data di creazione in ordine discendente.
     *
     * @param finished indica se la partita è finita o meno
     * @param email    l'email del giocatore
     * @return un'istanza di `Optional<Game>` contenente la prima partita trovata (se presente), altrimenti un'istanza vuota
     */
    Optional<Game> findFirstByFinishedAndPlayer_EmailOrderByCreatedAtDesc(boolean finished, String email);
}
