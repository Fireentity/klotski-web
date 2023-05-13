package it.klotski.web.game.domain.move;

import it.klotski.web.game.domain.game.Game;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * Classe che rappresenta l'oggetto "mossa", costituito da: id, partita di appartenenza, tile di appartenenza, direzione
 * di movimento, configurazione di appartenenza, data di creazione, data di aggiornamento.
 */
@Setter
@Getter
@Entity
@Table(name = "moves")
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Game game;
    private int tileId;
    @Enumerated(EnumType.ORDINAL)
    private Direction direction;
    private String boardHash;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
