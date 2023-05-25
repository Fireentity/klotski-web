package it.klotski.web.game.repositories;

import it.klotski.web.game.domain.move.Move;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository per l'accesso ai dati delle mosse.
 *
 * L'interfaccia `IMoveRepository` estende l'interfaccia `PagingAndSortingRepository` e `CrudRepository` di Spring Data
 * per fornire operazioni di accesso ai dati delle mosse.
 *
 * Viene utilizzata l'annotazione `@Repository` per indicare che questa interfaccia è un componente di repository gestito da Spring.
 */
@Repository
public interface IMoveRepository extends PagingAndSortingRepository<Move, Long>, CrudRepository<Move, Long> {

    /**
     * Trova tutte le mosse di una partita, ordinate per data di creazione in ordine ascendente.
     *
     * @param game     la partita di cui cercare le mosse
     * @param pageable le informazioni di paginazione
     * @return una lista di oggetti `Move` corrispondenti alle mosse trovate
     */
    List<Move> findAllByGame_IdOrderByCreatedAtAsc(long gameId, Pageable pageable);

    /**
     * Elimina tutte le mosse di una partita.
     *
     * @param game la partita di cui eliminare le mosse
     */
    void deleteAllByGame_Id(long gameId);

    /**
     * Trova la prima mossa di una partita, ordinata per data di creazione in ordine discendente.
     *
     * @param game la partita di cui cercare la prima mossa
     * @return un'istanza di `Optional<Move>` contenente la prima mossa trovata (se presente), altrimenti un'istanza vuota
     */
    Optional<Move> findFirstByGame_IdOrderByCreatedAtDesc(long gameId);

    void deleteById(long id);
}
