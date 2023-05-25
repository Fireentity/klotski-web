package it.klotski.web.game.repositories;

import it.klotski.web.game.domain.game.GameView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGameViewRepository extends CrudRepository<GameView, Long> {
    Optional<GameView> findGameViewById(long gameId);

    List<GameView> findAllByPlayer_EmailOrderByCreatedAtDesc(String email, Pageable pageable);

    Optional<GameView> findFirstByFinishedAndPlayer_EmailOrderByCreatedAtDesc(boolean finished, String email);
}
