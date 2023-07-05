package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.domain.game.GameView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static it.klotski.web.game.constants.ApplicationConstants.DATE_FORMAT;

/**
 * Questa classe rappresenta la risposta delle informazioni di un gioco.
 * La classe `GameInfoResponse` contiene informazioni specifiche su un gioco, come l'identificatore,
 * l'ID della configurazione di partenza, la data di creazione formattata, il numero di mosse effettuate,
 * la durata del gioco e lo stato di completamento.
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
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
     * Lo stato di completamento del gioco.
     */
    private final boolean finished;

    /**
     * Crea un'istanza di `GameInfoResponse` a partire da un oggetto `GameView`.
     *
     * @param game L'oggetto `GameView` da cui ottenere le informazioni.
     * @return Un'istanza di `GameInfoResponse` contenente le informazioni del gioco.
     */
    public static GameInfoResponse from(GameView game) {
        return new GameInfoResponse(game.getId(),
                game.getStartConfigurationId(),
                DATE_FORMAT.format(game.getCreatedAt()),
                game.getMoves(),
                game.isFinished());
    }
}
