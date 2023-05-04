package it.klotski.web.game.repositories;

import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.move.Move;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMoveRepository extends PagingAndSortingRepository<Move, Long>, CrudRepository<Move, Long> {
    List<Move> findAllByGame(Game game, Pageable pageable);
    void deleteAllByGame(Game game);
    Optional<Move> findFirstByGameOrderByCreatedAtDesc(Game game);
}
