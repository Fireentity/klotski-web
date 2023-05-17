package it.klotski.web.game.domain.move;

import it.klotski.web.game.domain.game.Game;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "moves")

/**
 * Classe che rappresenta un'istanza di "mossa" all'interno del gioco.
 *
 * La classe Move rappresenta un'operazione di spostamento effettuata durante una partita.
 * Contiene informazioni come l'identificatore, la partita di appartenenza, la tessera coinvolta nella mossa,
 * la direzione di movimento, la configurazione di appartenenza, la data di creazione e la data di aggiornamento.
 */
public class Move {
    /**
     * L'identificatore univoco della mossa.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * La partita di appartenenza in cui è stata effettuata la mossa.
     */
    @OneToOne
    private Game game;

    /**
     * L'identificatore della tessera coinvolta nella mossa.
     */
    private int tileId;

    /**
     * La direzione di movimento della tessera.
     */
    @Enumerated(EnumType.ORDINAL)
    private Direction direction;

    /**
     * L'hash del tabellone di gioco dopo l'effettuazione della mossa.
     */
    private String boardHash;

    /**
     * La data di creazione della mossa.
     */
    @CreationTimestamp
    private Timestamp createdAt;

    /**
     * La data di aggiornamento della mossa.
     */
    @UpdateTimestamp
    private Timestamp updatedAt;
}
