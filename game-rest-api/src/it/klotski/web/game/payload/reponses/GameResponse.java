package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.GameView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static it.klotski.web.game.constants.ApplicationConstants.DATE_FORMAT;

/**
 * Questa classe rappresenta la risposta di un gioco completo.
 * La classe `GameResponse` contiene le informazioni complete di un gioco, inclusi l'identificatore,
 * l'ID della configurazione di partenza, la data di creazione formattata, la durata del gioco,
 * lo stato di completamento e il tabellone di gioco.
 */
@Getter
@RequiredArgsConstructor
public class GameResponse {
    /**
     * L'identificatore del gioco.
     */
    private final long id;

    /**
     * L'ID della configurazione di partenza del gioco.
     */
    private final int startConfigurationId;

    /**
     * La data di creazione del gioco formattata come stringa.
     */
    private final String date;

    /**
     * Lo stato di completamento del gioco.
     */
    private final boolean finished;

    /**
     * Il numero di mosse effettuate nel gioco.
     */
    private final int moves;

    /**
     * Il tabellone di gioco.
     */
    private final Board board;

    /**
     * Crea un'istanza di `GameResponse` a partire da un oggetto `GameView` e un tabellone di gioco.
     *
     * @param game  L'oggetto `GameView` da cui ottenere le informazioni.
     * @param board Il tabellone di gioco.
     * @return Un'istanza di `GameResponse` contenente le informazioni complete del gioco.
     */
    public static GameResponse from(GameView game, Board board) {
        return new GameResponse(game.getId(),
                game.getStartConfigurationId(),
                DATE_FORMAT.format(game.getCreatedAt()),
                game.isFinished(),
                game.getMoves(),
                board);
    }

    /**
     * Verifica se l'oggetto `GameResponse` è uguale a un altro oggetto.
     *
     * @param o L'oggetto da confrontare con l'istanza corrente di `GameResponse`.
     * @return `true` se gli oggetti sono uguali, `false` altrimenti.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameResponse that = (GameResponse) o;

        if (getId() != that.getId()) return false;
        if (getStartConfigurationId() != that.getStartConfigurationId()) return false;
        if (isFinished() != that.isFinished()) return false;
        if (getMoves() != that.getMoves()) return false;
        if (!getDate().equals(that.getDate())) return false;
        return getBoard().equals(that.getBoard());
    }

    /**
     * Restituisce l'hashCode dell'oggetto `GameResponse`.
     *
     * @return L'hashCode calcolato per l'istanza corrente di `GameResponse`.
     */
    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getStartConfigurationId();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + (isFinished() ? 1 : 0);
        result = 31 * result + getMoves();
        result = 31 * result + getBoard().hashCode();
        return result;
    }
}
