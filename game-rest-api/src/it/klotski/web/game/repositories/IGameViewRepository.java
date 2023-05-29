package it.klotski.web.game.repositories;

import it.klotski.web.game.domain.game.GameView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository per l'accesso ai dati delle visualizzazioni delle partite.
 * L'interfaccia `IGameViewRepository` estende l'interfaccia `CrudRepository` di Spring Data per fornire operazioni di
 * accesso ai dati delle visualizzazioni delle partite.
 * Viene utilizzata l'annotazione `@Repository` per indicare che questa interfaccia è un componente di repository gestito da Spring.
 */
@Repository
public interface IGameViewRepository extends CrudRepository<GameView, Long> {
    /**
     * Trova una visualizzazione di una partita dato il suo ID.
     *
     * @param gameId l'ID della partita di cui cercare la visualizzazione
     * @return un'istanza di `Optional<GameView>` contenente la visualizzazione della partita trovata (se presente), altrimenti un'istanza vuota
     */
    Optional<GameView> findGameViewById(long gameId);

    /**
     * Trova tutte le visualizzazioni di partite di un determinato utente, ordinate per data di creazione in ordine discendente.
     *
     * @param email    l'email dell'utente di cui cercare le visualizzazioni delle partite
     * @param pageable le informazioni di paginazione
     * @return una lista di oggetti `GameView` corrispondenti alle visualizzazioni delle partite trovate
     */
    List<GameView> findAllByPlayer_EmailOrderByCreatedAtDesc(String email, Pageable pageable);

    /**
     * Trova la prima visualizzazione di una partita non finita di un determinato utente, ordinata per data di creazione in ordine discendente.
     *
     * @param finished lo stato di completamento della partita (true per finita, false per non finita)
     * @param email    l'email dell'utente di cui cercare la visualizzazione della partita
     * @return un'istanza di `Optional<GameView>` contenente la prima visualizzazione della partita non finita trovata (se presente), altrimenti un'istanza vuota
     */
    Optional<GameView> findFirstByFinishedAndPlayer_EmailOrderByCreatedAtDesc(boolean finished, String email);
}
