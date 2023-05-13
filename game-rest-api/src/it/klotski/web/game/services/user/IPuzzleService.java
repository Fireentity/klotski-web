package it.klotski.web.game.services.user;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.payload.requests.MoveRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia per la gestione dei servizi legati al gioco (trovare partite, trovare mosse, creare partite, cancellare
 * mosse, calcolare configurazione corrente)
 */
public interface IPuzzleService {
    /**
     * Funzione per la ricerca di tutte le partite di un dato utente.
     * @param email per identificare l'utente.
     * @param pageable per definire il numero di partite per pagina.
     * @return la lista di partite effettuate dall'utente.
     */
    List<Game> findGamesByUser(String email, Pageable pageable);

    /**
     * Funzione per trovare l'ultima partita non finita di un dato user a partire dalla sua email.
     * @param email per identificare l'utente.
     * @return l'ultima partita non finita di un dato user.
     */
    Optional<Game> findLastUnfinishedGame(String email);

    /**
     * Funzione per trovare una partita a partire dal suo id.
     * @param id identificativo di una partita.
     * @return la partita trovata con quel determinato id.
     */
    Optional<Game> findGameById(long id);

    /**
     * Funzione per trovare le mosse di una determinata partita.
     * @param game partita di cui si vogliono trovare le mosse.
     * @param pageable per definire il numero di mosse per pagina.
     * @return la lista di mosse associata alla partita contenuta in game.
     */
    List<Move> findMovesByGame(Game game, Pageable pageable);

    /**
     * Funzione per cancellare le mosse associate a una partita.
     * @param game partita di cui bisogna cancellare le mosse.
     */
    void deleteMovesByGame(Game game);

    /**
     * Funzione per trovare l'ultima mossa di una partita.
     * @param game partita di cui si vuole cercare l'ultima mossa.
     * @return l'ultima mossa della partita contenuta in game.
     */
    Optional<Move> findLastMove(Game game);

    /**
     * Funzione per creare una nuova mossa e salvarla nella tabella delle mosse.
     * @param moveRequest mossa contenente le informazioni sul pezzo e sulla direzione di movimento.
     * @param game partita in cui avviene la mossa.
     * @param board griglia in cui avviene la mossa.
     * @param boardHash stringa che identifica in maniera univoca una disposizione della griglia.
     */
    void createMove(MoveRequest moveRequest, Game game, ITile[][] board, String boardHash);

    /**
     * Funzione che crea una partita da una determinata configurazione.
     * @param email per identificare l'utente.
     * @param configurationId id della configurazione di start.
     * @return la partita creata a partire dalla configurazione scelta.
     */
    GameResponse createGameFromConfiguration(String email, int configurationId);

    /**
     * Funzione che a partire dalle mosse di una partita ricava la configurazione corrente.
     * @param game partita di cui si vuole ricavare la configurazione corrente.
     * @return la configurazione corrente della partita contenuta in game.
     */
    Board calculateCurrentConfiguration(Game game);
}
