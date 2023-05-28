package it.klotski.web.game.controllers;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.game.IGame;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.GameNotFoundException;
import it.klotski.web.game.exceptions.InvalidBoardConfigurationException;
import it.klotski.web.game.exceptions.UserNotFoundException;
import it.klotski.web.game.payload.reponses.MoveResponse;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.payload.requests.UndoRequest;
import it.klotski.web.game.repositories.IUserRepository;
import it.klotski.web.game.services.IPuzzleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Il controller che gestisce le richieste relative agli spostamenti delle tessere nel gioco.
 * Fornisce un endpoint per spostare una tessera in una direzione specificata.
 * Fornisce anche endpoint per ottenere la lista degli spostamenti effettuati in un gioco specifico e per ripristinare
 * il gioco allo stato iniziale.
 */
@RestController
@RequestMapping(path = "/api/moves")
public class MoveController {

    /**
     * Il repository degli utenti utilizzato per recuperare informazioni sull'utente autenticato.
     */
    private final IUserRepository userRepository;

    /**
     * Il servizio del gioco utilizzato per eseguire operazioni sul gioco.
     */
    private final IPuzzleService puzzleService;

    /**
     * La lista delle configurazioni di gioco disponibili.
     */
    private final List<Board> boards;

    /**
     * Costruisce un'istanza del controller dei movimenti del gioco.
     *
     * @param userRepository Il repository degli utenti utilizzato per recuperare informazioni sull'utente autenticato.
     * @param puzzleService  Il servizio del gioco utilizzato per eseguire operazioni sul gioco.
     * @param boards         La lista delle configurazioni di gioco disponibili.
     */
    @Autowired
    public MoveController(IUserRepository userRepository, IPuzzleService puzzleService, List<Board> boards) {
        this.userRepository = userRepository;
        this.puzzleService = puzzleService;
        this.boards = boards;
    }

    /**
     * Effettua il movimento di una tessera nel gioco.
     *
     * @param moveRequest La richiesta di movimento contenente le informazioni sulla tessera da spostare e la direzione del movimento.
     * @return La risposta contenente le informazioni sulle tessere dopo il movimento.
     * @throws GameNotFoundException              Se il gioco specificato non è stato trovato.
     * @throws UserNotFoundException              Se l'utente autenticato non è stato trovato.
     * @throws InvalidBoardConfigurationException Se la configurazione del tabellone non è valida.
     */
    @PostMapping
    public ResponseEntity<MoveResponse> moveTile(@RequestBody MoveRequest moveRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Game game = puzzleService.findGameById(moveRequest.getGameId()).orElseThrow(GameNotFoundException::new);
        //Il gioco deve appartenere all'user che ha fatto la richiesta
        if (game.getPlayer().getId() != user.getId()) {
            throw new GameNotFoundException();
        }

        return puzzleService.moveTile(moveRequest, game);
    }

    /**
     * Ottiene la lista dei movimenti effettuati in un gioco specifico.
     *
     * @param gameId L'ID della partita.
     * @param page   La pagina dei risultati da restituire (valore predefinito: 0).
     * @param size   La dimensione della pagina dei risultati (valore predefinito: 10).
     * @return La risposta contenente la lista dei movimenti.
     * @throws GameNotFoundException Se il gioco specificato non è stato trovato.
     * @throws UserNotFoundException Se l'utente autenticato non è stato trovato.
     */
    @GetMapping
    public ResponseEntity<List<Move>> getMoves(@RequestBody long gameId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Game game = puzzleService.findGameById(gameId).orElseThrow(GameNotFoundException::new);
        if (game.getPlayer().getId() != user.getId()) {
            throw new GameNotFoundException();
        }
        return ResponseEntity.ok(puzzleService.findMovesByGame_Id(game.getId(), PageRequest.of(page, size)));
    }

    @PostMapping("/undo")
    public ResponseEntity<MoveResponse> undo(@RequestBody UndoRequest undoRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Game game = puzzleService.findGameById(undoRequest.getGameId()).orElseThrow(GameNotFoundException::new);
        if (game.getPlayer().getId() != user.getId()) {
            throw new GameNotFoundException();
        }
        return ResponseEntity.ok(new MoveResponse(false, puzzleService.undo(game, undoRequest.getTiles())));
    }

    /**
     * Reimposta un gioco specifico eliminando tutti i movimenti effettuati.
     *
     * @param gameId L'ID del gioco da reimpostare.
     * @return La risposta contenente la configurazione iniziale del gioco.
     * @throws GameNotFoundException Se il gioco specificato non è stato trovato.
     * @throws GameNotFoundException Se l'utente autenticato non è il proprietario del gioco.
     * @throws GameNotFoundException Se il gioco è già terminato.
     */
    @Transactional
    @DeleteMapping
    public ResponseEntity<MoveResponse> reset(@RequestParam long gameId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        IGame game = puzzleService.findGameById(gameId).orElseThrow(GameNotFoundException::new);

        if (user.getId() != game.getPlayer().getId()) {
            throw new GameNotFoundException();
        }

        if (game.isFinished()) {
            throw new GameNotFoundException();
        }

        Board board = boards.get(game.getStartConfigurationId());

        puzzleService.deleteMovesByGame_Id(game.getId());
        return ResponseEntity.ok(new MoveResponse(false, board.getTiles()));
    }
}
