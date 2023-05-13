package it.klotski.web.game.domain.game;

import it.klotski.web.game.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

/**
 * Classe che rappresenta l'oggetto "partita", costituito da: id, user, configurazione iniziale, data di creazione, data
 * di aggiornamento, numero di mosse, stato (finita o in corso).
 */
@Setter
@Entity
@Getter
@Table(name = "games")
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User player;
    private int startConfigurationId;
    private long duration;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    private int moves;
    private boolean finished;
}
