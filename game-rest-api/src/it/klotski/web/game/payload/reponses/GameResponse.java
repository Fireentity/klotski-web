package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.domain.game.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameResponse {
    private final long id;
    private final int startConfigurationId;
    private final long date;
    private final long duration;
    private final boolean finished;

    public static GameResponse from(Game game) {
        return new GameResponse(game.getId(),
                game.getStartConfigurationId(),
                game.getDate(),
                game.getDuration(),
                game.isFinished());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameResponse that = (GameResponse) o;

        if (id != that.id) return false;
        if (startConfigurationId != that.startConfigurationId) return false;
        if (date != that.date) return false;
        if (duration != that.duration) return false;
        return finished == that.finished;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + startConfigurationId;
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (finished ? 1 : 0);
        return result;
    }
}
