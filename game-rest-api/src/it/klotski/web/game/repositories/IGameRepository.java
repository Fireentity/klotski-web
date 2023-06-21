package it.klotski.web.game.repositories;

import it.klotski.web.game.domain.game.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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
     * Trova una partita per ID.
     *
     * @param id l'ID della partita da trovare
     * @return un'istanza di `Optional<Game>` contenente la partita trovata (se presente), altrimenti un'istanza vuota
     */
    Optional<Game> findGameById(long id);
}
