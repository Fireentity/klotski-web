package it.klotski.web.game.repositories;

import it.klotski.web.game.domain.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGameRepository extends PagingAndSortingRepository<Game, Long>, CrudRepository<Game, Long> {
    List<Game> findAllByPlayer_Email(String email, Pageable pageable);
    Optional<Game> findGameById(long id);
}
