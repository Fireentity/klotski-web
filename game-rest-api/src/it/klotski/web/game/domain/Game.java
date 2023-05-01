package it.klotski.web.game.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Entity
@Getter
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User player;
    private int startConfigurationId;
    private long duration;
    private long date;
    private boolean isFinished;
}
