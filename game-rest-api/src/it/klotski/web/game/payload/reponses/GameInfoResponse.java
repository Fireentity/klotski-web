package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.domain.game.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static it.klotski.web.game.constants.ApplicationConstants.DATE_FORMAT;

@Getter
@RequiredArgsConstructor

/**
 * Questa classe rappresenta la risposta delle informazioni di un gioco.
 *
 * La classe GameInfoResponse contiene informazioni specifiche su un gioco, come l'identificatore,
 * l'ID della configurazione di partenza, la data di creazione formattata, il numero di mosse effettuate,
 * la durata del gioco e lo stato di completamento.
 */
public class GameInfoResponse {
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
     * Il numero di mosse effettuate nel gioco.
     */
    private final int moves;

    /**
     * La durata del gioco in millisecondi.
     */
    private final long duration;

    /**
     * Lo stato di completamento del gioco.
     */
    private final boolean finished;

    /**
     * Crea un'istanza di GameInfoResponse a partire da un oggetto Game.
     *
     * @param game L'oggetto Game da cui ottenere le informazioni.
     * @return Un'istanza di GameInfoResponse contenente le informazioni del gioco.
     */
    public static GameInfoResponse from(Game game) {
        return new GameInfoResponse(game.getId(),
                game.getStartConfigurationId(),
                DATE_FORMAT.format(game.getCreatedAt()),
                game.getMoves(),
                game.getDuration(),
                game.isFinished());
    }

    /**
     * Verifica se l'oggetto GameInfoResponse è uguale a un altro oggetto.
     *
     * @param o L'oggetto da confrontare con l'istanza corrente di GameInfoResponse.
     * @return true se gli oggetti sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameInfoResponse that = (GameInfoResponse) o;

        if (getId() != that.getId()) return false;
        if (getStartConfigurationId() != that.getStartConfigurationId()) return false;
        if (getDuration() != that.getDuration()) return false;
        if (isFinished() != that.isFinished()) return false;
        return getDate().equals(that.getDate());
    }

    /**
     * Restituisce l'hashCode dell'oggetto GameInfoResponse.
     *
     * @return L'hashCode calcolato per l'istanza corrente di GameInfoResponse.
     */
    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getStartConfigurationId();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + (int) (getDuration() ^ (getDuration() >>> 32));
        result = 31 * result + (isFinished() ? 1 : 0);
        return result;
    }
}
