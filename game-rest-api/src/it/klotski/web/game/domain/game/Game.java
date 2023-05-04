package it.klotski.web.game.domain.game;

import it.klotski.web.game.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Entity
@Getter
@Table(name = "games")
@AllArgsConstructor
@NoArgsConstructor
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
