package it.klotski.web.game.services;

import com.google.gson.Gson;
import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.game.GameView;
import it.klotski.web.game.domain.game.IGame;
import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.strategy.RectangularTileMatrixInsertionStrategy;
import it.klotski.web.game.domain.tile.strategy.RectangularTileMoveValidationStrategy;
import it.klotski.web.game.domain.tile.strategy.WinConditionStrategy;
import it.klotski.web.game.domain.tile.visitor.ITileVisitor;
import it.klotski.web.game.domain.tile.visitor.RectangularTileVisitor;
import it.klotski.web.game.domain.tile.visitor.WinningTileVisitor;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.*;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.payload.reponses.MoveResponse;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.repositories.IGameRepository;
import it.klotski.web.game.repositories.IGameViewRepository;
import it.klotski.web.game.repositories.IMoveRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static it.klotski.web.game.constants.ApplicationConstants.DEFAULT_PAGE_SIZE;

/**
 * Servizio per gestire le partite: trovare le partite, le mosse associate, la configurazione attuale, creare partite.
 */
@Service
public class PuzzleService implements IPuzzleService {
    private final List<Board> boards;
    private final IGameRepository gameRepository;
    private final IMoveRepository moveRepository;
    private final UserDetailsService userService;
    private final IGameViewRepository gameViewRepository;
    private final Gson gson;

    /**
     * Costruttore di classe.
     *
     * @param boards             griglia di gioco formata dai pezzi.
     * @param gameRepository     repository per le partite.
     * @param moveRepository     repository per le mosse.
     * @param userService        service per la ricerca degli utenti nel repository degli utenti.
     * @param gameViewRepository repository per le viste delle partite.
     * @param gson               oggetto Gson per la serializzazione e deserializzazione JSON.
     */
    public PuzzleService(List<Board> boards,
                         IGameRepository gameRepository,
                         IMoveRepository moveRepository,
                         UserDetailsService userService,
                         IGameViewRepository gameViewRepository,
                         Gson gson) {
        this.boards = boards;
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
        this.userService = userService;
        this.gameViewRepository = gameViewRepository;
        this.gson = gson;
    }

    /**
     * Trova tutte le partite di un determinato utente.
     *
     * @param email    l'email per identificare l'utente.
     * @param pageable l'oggetto Pageable per definire il numero di partite per pagina.
     * @return la lista delle partite effettuate dall'utente.
     */
    @Override
    public List<GameView> findGamesByUser(String email, Pageable pageable) {
        return gameViewRepository.findAllByPlayer_EmailOrderByCreatedAtDesc(email, pageable);
    }

    /**
     * Trova l'ultima partita non finita di un determinato utente.
     *
     * @param email l'email per identificare l'utente.
     * @return l'ultima partita non finita dell'utente.
     */
    @Override
    public Optional<GameView> findLastUnfinishedGame(String email) {
        return gameViewRepository.findFirstByFinishedAndPlayer_EmailOrderByCreatedAtDesc(false, email);
    }

    /**
     * Trova una partita dato il suo ID.
     *
     * @param id l'ID della partita.
     * @return la partita trovata con l'ID specificato.
     */
    @Override
    public Optional<Game> findGameById(long id) {
        return gameRepository.findGameById(id);
    }

    /**
     * Trova una GameView dato il suo ID.
     *
     * @param id l'ID della GameView.
     * @return la GameView trovata con l'ID specificato.
     */
    @Override
    public Optional<GameView> findGameViewById(long id) {
        return gameViewRepository.findGameViewById(id);
    }

    /**
     * Trova le mosse di una partita.
     *
     * @param gameId   l'ID della partita.
     * @param pageable l'oggetto Pageable per definire il numero di mosse per pagina.
     * @return la lista delle mosse associate alla partita.
     */
    @Override
    public List<Move> findMovesByGame_Id(long gameId, Pageable pageable) {
        return moveRepository.findAllByGame_IdOrderByCreatedAtAsc(gameId, pageable);
    }

    /**
     * Cancella tutte le mosse associate a una partita.
     *
     * @param gameId l'ID della partita.
     */
    @Override
    public void deleteMovesByGame_Id(long gameId) {
        moveRepository.deleteAllByGame_Id(gameId);
    }

    /**
     * Trova l'ultima mossa di una partita.
     *
     * @param gameId l'ID della partita.
     * @return l'ultima mossa della partita.
     */
    @Override
    public Optional<Move> findLastMove(long gameId) {
        return moveRepository.findFirstByGame_IdOrderByCreatedAtDesc(gameId);
    }

    /**
     * Imposta la partita come finita.
     *
     * @param game la partita da segnare come finita.
     */
    @Override
    public void setGameFinished(Game game) {
        game.setFinished(true);
        gameRepository.save(game);
    }

    /**
     * Esegue una mossa sulle tessere di una partita.
     *
     * @param moveRequest la richiesta di mossa contenente le informazioni sulla tessera e la direzione di movimento.
     * @param game        la partita in cui viene eseguita la mossa.
     * @return una ResponseEntity contenente la risposta alla mossa.
     */
    @Override
    public ResponseEntity<MoveResponse> moveTile(MoveRequest moveRequest, Game game) {
        Board startConfiguration = boards.get(game.getStartConfigurationId());

        // Inserisce le tessere nella matrice "board" utilizzando una strategia specifica e applicando i visitatori corrispondenti a ciascuna tessera.
        ITile[][] board = new ITile[startConfiguration.getBoardHeight()][startConfiguration.getBoardWidth()];
        RectangularTileMatrixInsertionStrategy insertionStrategy = new RectangularTileMatrixInsertionStrategy(board,
                startConfiguration.getBoardHeight(),
                startConfiguration.getBoardWidth());
        List<ITileVisitor> insertionVisitors = List.of(new RectangularTileVisitor(insertionStrategy), new WinningTileVisitor(insertionStrategy));
        moveRequest.getTiles().forEach(tile -> insertionVisitors.forEach(tile::accept));

        // Crea una RectangularTileMoveValidationStrategy che viene utilizzata per validare una mossa sulla matrice board in base alla direzione specificata in moveRequest.getDirection()
        RectangularTileMoveValidationStrategy validationStrategy = new RectangularTileMoveValidationStrategy(
                board,
                moveRequest.getDirection());

        // Crea due visitatori (RectangularTileVisitor e WinningTileVisitor) che vengono passati alla strategia di validazione. I visitatori sono utilizzati per visitare le tessere coinvolte nella mossa e applicare la logica di validazione specifica.
        List<ITileVisitor> visitors = List.of(new RectangularTileVisitor(validationStrategy), new WinningTileVisitor(validationStrategy));
        visitors.forEach(visitor -> moveRequest.getTileToMove().accept(visitor));

        String boardHash = DigestUtils.md5DigestAsHex(gson.toJson(moveRequest.getTiles()).getBytes(StandardCharsets.UTF_8));

        // Controlla se il movimento è valido in base alla strategia di validazione, in tal caso ritorna un oggetto MoveResponse che contiene le tessere senza effettuare alcuna modifica.
        if (!validationStrategy.isValid()) {
            return ResponseEntity.ok(new MoveResponse(false, moveRequest.getTiles()));
        }

        // Verifica se la griglia è coerente con l'ultimo movimento registrato
        Optional<Move> lastMove = this.findLastMove(game.getId());
        if (lastMove.isPresent() && !lastMove.get().getBoardHash().equals(boardHash)) {
            throw new InvalidBoardConfigurationException();
        }

        ITile movedTile = moveRequest.getTileToMove().move(moveRequest.getDirection());
        TreeSet<ITile> responseTiles = new TreeSet<>(moveRequest.getTiles());
        responseTiles.remove(moveRequest.getTileToMove());
        responseTiles.add(movedTile);

        // Valore univoco che rappresenta l'oggetto responseTiles, in modo da poterlo utilizzare per confrontare e verificare l'integrità dei dati successivamente.
        String newBoardHash = DigestUtils.md5DigestAsHex(gson.toJson(responseTiles).getBytes(StandardCharsets.UTF_8));
        this.createMove(moveRequest, game, board, newBoardHash);

        WinConditionStrategy winConditionStrategy = new WinConditionStrategy(startConfiguration.getWinningX(), startConfiguration.getWinningY());
        WinningTileVisitor winningTileVisitor = new WinningTileVisitor(winConditionStrategy);
        movedTile.accept(winningTileVisitor);

        if (winConditionStrategy.isWinning()) {
            this.setGameFinished(game);
        }

        return ResponseEntity.ok(new MoveResponse(winConditionStrategy.isWinning(), responseTiles));
    }

    /**
     * Annulla l'ultima mossa di una partita.
     *
     * @param game  la partita in cui annullare la mossa.
     * @param tiles l'insieme delle tessere attuali.
     * @return l'insieme delle tessere dopo l'annullamento della mossa.
     */
    @Override
    public TreeSet<ITile> undo(IGame game, TreeSet<ITile> tiles) {
        Move move = moveRepository.findFirstByGame_IdOrderByCreatedAtDesc(game.getId()).orElseThrow(MoveNotFoundException::new);
        moveRepository.deleteById(move.getId());
        ITile tile = tiles.stream()
                .filter(iteratedTile -> iteratedTile.getId() == move.getTileId())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Impossibile annullare il movimento per la tessera %s", move.getTileId())));
        tiles.remove(tile);
        tiles.add(tile.move(Direction.opposite(move.getDirection())));
        return tiles;
    }

    /**
     * Crea una nuova mossa e la salva nel repository delle mosse.
     *
     * @param moveRequest la richiesta di mossa contenente le informazioni sulla tessera e la direzione di movimento.
     * @param game        la partita in cui viene creata la mossa.
     * @param board       la griglia in cui avviene la mossa.
     * @param boardHash   la stringa che identifica in modo univoco una disposizione della griglia.
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
     * Cambia la configurazione di partenza di una partita.
     *
     * @param game                la partita in cui cambiare la configurazione di partenza.
     * @param startConfigurationId l'ID della nuova configurazione di partenza.
     */
    @Override
    public void changeStartConfiguration(Game game, int startConfigurationId) {
        GameView gameView = gameViewRepository.findGameViewById(game.getId()).orElseThrow(GameNotFoundException::new);
        if (gameView.getMoves() != 0) {
            throw new GameAlreadyStartedException();
        }

        Board board = boards.get(startConfigurationId);
        if (board == null) {
            throw new ConfigurationNotFoundException();
        }

        game.setStartConfigurationId(startConfigurationId);
        gameRepository.save(game);
    }

    /**
     * Crea una partita a partire da una configurazione specifica.
     *
     * @param email             l'email per identificare l'utente.
     * @param configurationId   l'ID della configurazione di partenza.
     * @return la risposta contenente la partita creata.
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
        game = gameRepository.save(game);
        GameView gameView = gameViewRepository.findGameViewById(game.getId()).orElseThrow();
        return GameResponse.from(gameView, board);
    }

    /**
     * Calcola la configurazione corrente di una partita in base alle mosse effettuate.
     *
     * @param game la partita di cui calcolare la configurazione corrente.
     * @return la configurazione corrente della partita.
     */
    @Override
    public Board calculateCurrentConfiguration(IGame game) {
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Move> moves = this.findMovesByGame_Id(game.getId(), PageRequest.of(0, DEFAULT_PAGE_SIZE));
        Board board = boards.get(game.getStartConfigurationId());
        Map<Integer, ITile> tiles = new HashMap<>();
        board.getTiles().forEach(tile -> tiles.put(tile.getId(), tile));

        // Esegue tutte le mosse della partita sulla configurazione iniziale per ottenere la configurazione corrente
        while (!moves.isEmpty()) {
            moves.forEach(move -> tiles.put(move.getTileId(), tiles.get(move.getTileId()).move(move.getDirection())));
            pageable = pageable.next();
            moves = this.findMovesByGame_Id(game.getId(), pageable);
        }
        return board.withTiles(new TreeSet<>(tiles.values()));
    }
}
