package it.klotski.web.game.services.user;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.ConfigurationNotFoundException;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.repositories.IGameRepository;
import it.klotski.web.game.repositories.IMoveRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

import static it.klotski.web.game.constants.ApplicationConstants.DEFAULT_PAGE_SIZE;

/**
 * Service per gestire le partite: trovare le partite, le mosse associate, la configurazione attuale, creare partite.
 */
@Service
public class PuzzleService implements IPuzzleService {
    private final List<Board> boards;
    private final IGameRepository gameRepository;
    private final IMoveRepository moveRepository;
    private final UserDetailsService userService;

    /**
     * Costruttore di classe:
     * @param boards griglia di gioco formata dai pezzi.
     * @param gameRepository tabella dei giochi.
     * @param moveRepository tabella delle mosse.
     * @param userService service per la ricerca degli utenti nella tabella degli utenti.
     */
    public PuzzleService(List<Board> boards,
                         IGameRepository gameRepository,
                         IMoveRepository moveRepository,
                         UserDetailsService userService) {
        this.boards = boards;
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
        this.userService = userService;
    }

    /**
     * Funzione per la ricerca di tutte le partite di un dato utente.
     * @param email per identificare l'utente.
     * @param pageable per definire il numero di partite per pagina.
     * @return la lista di partite effettuate dall'utente.
     */
    @Override
    public List<Game> findGamesByUser(String email, Pageable pageable) {
        return gameRepository.findAllByPlayer_Email(email, pageable);
    }

    /**
     * Funzione per trovare l'ultima partita non finita di un dato user a partire dalla sua email.
     * @param email per identificare l'utente.
     * @return l'ultima partita non finita di un dato user.
     */
    @Override
    public Optional<Game> findLastUnfinishedGame(String email) {
        return gameRepository.findFirstByFinishedAndPlayer_EmailOrderByCreatedAtDesc(false, email);
    }

    /**
     * Funzione per trovare una partita a partire dal suo id.
     * @param id identificativo di una partita.
     * @return la partita trovata con quel determinato id.
     */
    @Override
    public Optional<Game> findGameById(long id) {
        return gameRepository.findGameById(id);
    }

    /**
     * Funzione per trovare le mosse di una determinata partita.
     * @param game partita di cui si vogliono trovare le mosse.
     * @param pageable per definire il numero di mosse per pagina.
     * @return la lista di mosse associata alla partita contenuta in game.
     */
    @Override
    public List<Move> findMovesByGame(Game game, Pageable pageable) {
        return moveRepository.findAllByGameOrderByCreatedAtAsc(game, pageable);
    }

    /**
     * Funzione per cancellare le mosse associate a una partita.
     * @param game partita di cui bisogna cancellare le mosse.
     */
    @Override
    public void deleteMovesByGame(Game game) {
        moveRepository.deleteAllByGame(game);
    }

    /**
     * Funzione per trovare l'ultima mossa di una partita.
     * @param game partita di cui si vuole cercare l'ultima mossa.
     * @return l'ultima mossa della partita contenuta in game.
     */
    @Override
    public Optional<Move> findLastMove(Game game) {
        return moveRepository.findFirstByGameOrderByCreatedAtDesc(game);
    }

    /**
     * Funzione per creare una nuova mossa e salvarla nella tabella delle mosse.
     * @param moveRequest mossa contenente le informazioni sul pezzo e sulla direzione di movimento.
     * @param game partita in cui avviene la mossa.
     * @param board griglia in cui avviene la mossa.
     * @param boardHash stringa che identifica in maniera univoca una disposizione della griglia.
     */
    @Override
    public void createMove(MoveRequest moveRequest, Game game, ITile[][] board, String boardHash) {
        Direction direction = moveRequest.getDirection();

        Move move = new Move();
        move.setTileId(moveRequest.getTileToMove().getId());
        move.setGame(game);
        move.setDirection(moveRequest.getDirection());
        move.setBoardHash(boardHash);
        move.setDirection(direction);

        moveRepository.save(move);
    }

    /**
     * Funzione che crea una partita da una determinata configurazione.
     * @param email per identificare l'utente.
     * @param configurationId id della configurazione di start.
     * @return la partita creata a partire dalla configurazione scelta.
     */
    @Override
    public GameResponse createGameFromConfiguration(String email, int configurationId) {
        if (configurationId >= boards.size()) {
            throw new ConfigurationNotFoundException();
        }
        User user = (User) userService.loadUserByUsername(email);
        Game game = new Game();
        game.setPlayer(user);
        game.setStartConfigurationId(configurationId);

        Board board = boards.get(configurationId);
        return GameResponse.from(gameRepository.save(game), board);
    }

    /**
     * Funzione che a partire dalle mosse di una partita ricava la configurazione corrente.
     * @param game partita di cui si vuole ricavare la configurazione corrente.
     * @return la configurazione corrente della partita contenuta in game.
     */
    @Override
    public Board calculateCurrentConfiguration(Game game) {
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        //Da tutte le mosse esistenti ne prende DEFAULT_PAGE_SIZE per volta
        List<Move> moves = this.findMovesByGame(game, PageRequest.of(0, DEFAULT_PAGE_SIZE));
        Board board = boards.get(game.getStartConfigurationId());
        Map<Integer, ITile> tiles = new HashMap<>();
        board.getTiles().forEach(tile -> tiles.put(tile.getId(), tile));

        // esegue tutte le mosse della partita alla configurazione iniziale per ottenere la configurazione corrente
        while(!moves.isEmpty()) {
            moves.forEach(move -> tiles.put(move.getTileId(), tiles.get(move.getTileId()).move(move.getDirection())));
            pageable = pageable.next();
            moves = this.findMovesByGame(game, pageable);
        }
        return board.withTiles(new TreeSet<>(tiles.values()));
    }

}
