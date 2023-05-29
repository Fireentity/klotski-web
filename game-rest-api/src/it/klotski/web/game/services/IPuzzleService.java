package it.klotski.web.game.services;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.game.GameView;
import it.klotski.web.game.domain.game.IGame;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.payload.reponses.MoveResponse;
import it.klotski.web.game.payload.requests.MoveRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

/**
 * Interfaccia per la gestione dei servizi legati al gioco (trovare partite, trovare mosse, creare partite, cancellare
 * mosse, calcolare configurazione corrente).
 */
public interface IPuzzleService {
    /**
     * Trova tutte le partite di un determinato utente.
     *
     * @param email    l'email per identificare l'utente.
     * @param pageable l'oggetto Pageable per definire il numero di partite per pagina.
     * @return la lista delle partite effettuate dall'utente.
     */
    List<GameView> findGamesByUser(String email, Pageable pageable);

    /**
     * Trova l'ultima partita non finita di un determinato utente.
     *
     * @param email l'email per identificare l'utente.
     * @return l'ultima partita non finita dell'utente.
     */
    Optional<GameView> findLastUnfinishedGame(String email);

    /**
     * Trova una partita dato il suo ID.
     *
     * @param id l'ID della partita.
     * @return la partita trovata con l'ID specificato.
     */
    Optional<Game> findGameById(long id);

    /**
     * Trova una partita dato il suo ID.
     *
     * @param id l'ID della partita.
     * @return la partita trovata con l'ID specificato.
     */
    Optional<GameView> findGameViewById(long id);

    /**
     * Trova le mosse di una determinata partita.
     *
     * @param gameId   l'ID della partita.
     * @param pageable l'oggetto Pageable per definire il numero di mosse per pagina.
     * @return la lista delle mosse associate alla partita.
     */
    List<Move> findMovesByGame_Id(long gameId, Pageable pageable);

    /**
     * Cancella tutte le mosse associate a una partita.
     *
     * @param gameId l'ID della partita.
     */
    void deleteMovesByGame_Id(long gameId);

    /**
     * Trova l'ultima mossa di una partita.
     *
     * @param gameId l'ID della partita.
     * @return l'ultima mossa della partita.
     */
    Optional<Move> findLastMove(long gameId);

    /**
     * Imposta la partita come finita.
     *
     * @param game la partita da segnare come finita.
     */
    void setGameFinished(Game game);

    /**
     * Esegue una mossa sulle tessere di una partita.
     *
     * @param moveRequest la richiesta di mossa contenente le informazioni sulla tessera e la direzione di movimento.
     * @param game        la partita in cui viene eseguita la mossa.
     * @return una ResponseEntity contenente la risposta alla mossa.
     */
    ResponseEntity<MoveResponse> moveTile(MoveRequest moveRequest, Game game);

    /**
     * Annulla l'ultima mossa di una partita.
     *
     * @param game  la partita in cui annullare la mossa.
     * @param tiles l'insieme delle tessere attuali.
     * @return l'insieme delle tessere dopo l'annullamento della mossa.
     */
    TreeSet<ITile> undo(IGame game, TreeSet<ITile> tiles);

    /**
     * Crea una nuova mossa e la salva nel repository delle mosse.
     *
     * @param moveRequest la richiesta di mossa contenente le informazioni sulla tessera e la direzione di movimento.
     * @param game        la partita in cui viene creata la mossa.
     * @param board       la griglia in cui avviene la mossa.
     * @param boardHash   la stringa che identifica in modo univoco una disposizione della griglia.
     */
    void createMove(MoveRequest moveRequest, Game game, ITile[][] board, String boardHash);

    /**
     * Cambia la configurazione di partenza di una partita.
     *
     * @param game                la partita in cui cambiare la configurazione di partenza.
     * @param startConfigurationId l'ID della nuova configurazione di partenza.
     */
    void changeStartConfiguration(Game game, int startConfigurationId);

    /**
     * Crea una partita a partire da una configurazione specifica.
     *
     * @param email             l'email per identificare l'utente.
     * @param configurationId   l'ID della configurazione di partenza.
     * @return la risposta contenente la partita creata.
     */
    GameResponse createGameFromConfiguration(String email, int configurationId);

    /**
     * Calcola la configurazione corrente di una partita in base alle mosse effettuate.
     *
     * @param game la partita di cui calcolare la configurazione corrente.
     * @return la configurazione corrente della partita.
     */
    Board calculateCurrentConfiguration(IGame game);
}
