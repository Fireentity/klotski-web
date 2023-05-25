package it.klotski.web.game.domain.game;

import it.klotski.web.game.domain.user.User;

import java.sql.Timestamp;

public interface IGame {
    long getId();

    User getPlayer();

    int getStartConfigurationId();

    Timestamp getCreatedAt();

    Timestamp getUpdatedAt();

    boolean isFinished();
}
