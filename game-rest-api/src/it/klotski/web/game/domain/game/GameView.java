package it.klotski.web.game.domain.game;

import it.klotski.web.game.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * Classe che rappresenta una vista del gioco.
 * La classe GameView implementa l'interfaccia IGame e fornisce informazioni
 * aggiuntive sul gioco, come il numero di mosse effettuate.
 */
@Setter
@Getter
@Entity
@Immutable
@Subselect("SELECT * FROM games_view")
@NoArgsConstructor
public class GameView implements IGame {
    /**
     * Identificatore univoco del gioco.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Il giocatore associato al gioco.
     */
    @OneToOne
    private User player;

    /**
     * L'ID della configurazione di partenza del gioco.
     */
    private int startConfigurationId;

    /**
     * Data e ora di creazione del gioco.
     */
    @CreationTimestamp
    private Timestamp createdAt;

    /**
     * Data e ora di aggiornamento del gioco.
     */
    @UpdateTimestamp
    private Timestamp updatedAt;

    /**
     * Lo stato di completamento del gioco.
     */
    private boolean finished;

    /**
     * Il numero di mosse effettuate nel gioco.
     */
    private int moves;
}
