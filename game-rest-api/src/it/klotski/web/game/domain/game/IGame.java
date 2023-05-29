package it.klotski.web.game.domain.game;

import it.klotski.web.game.domain.user.User;

import java.sql.Timestamp;

/**
 * Interfaccia per rappresentare un gioco.
 */
public interface IGame {
    /**
     * Restituisce l'ID del gioco.
     *
     * @return l'ID del gioco.
     */
    long getId();

    /**
     * Restituisce il giocatore associato al gioco.
     *
     * @return il giocatore associato al gioco.
     */
    User getPlayer();

    /**
     * Restituisce l'ID della configurazione di partenza del gioco.
     *
     * @return l'ID della configurazione di partenza del gioco.
     */
    int getStartConfigurationId();

    /**
     * Restituisce la data e l'ora di creazione del gioco.
     *
     * @return la data e l'ora di creazione del gioco.
     */
    Timestamp getCreatedAt();

    /**
     * Restituisce la data e l'ora dell'ultima modifica del gioco.
     *
     * @return la data e l'ora dell'ultima modifica del gioco.
     */
    Timestamp getUpdatedAt();

    /**
     * Verifica se il gioco è terminato.
     *
     * @return true se il gioco è terminato, false altrimenti.
     */
    boolean isFinished();
}
