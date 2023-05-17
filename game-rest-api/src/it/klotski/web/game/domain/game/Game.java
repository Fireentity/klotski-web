package it.klotski.web.game.domain.game;

import it.klotski.web.game.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

/**
 * Questa classe rappresenta un gioco Klotski.
 *
 * Un oggetto Game contiene informazioni sul gioco, come l'identificatore, il giocatore associato,
 * l'ID della configurazione di partenza, la durata del gioco, le informazioni sulla creazione e l'aggiornamento,
 * il numero di mosse effettuate e lo stato di completamento del gioco.
 */
@Setter
@Entity
@Getter
@Table(name = "games")
@NoArgsConstructor
public class Game {
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
     * La durata del gioco in millisecondi.
     */
    private long duration;

    /**
     * Data e ora di creazione del gioco.
     */
    @CreationTimestamp
    private Date createdAt;

    /**
     * Data e ora di aggiornamento del gioco.
     */
    @UpdateTimestamp
    private Date updatedAt;

    /**
     * Il numero di mosse effettuate nel gioco.
     */
    private int moves;

    /**
     * Lo stato di completamento del gioco.
     */
    private boolean finished;
}
