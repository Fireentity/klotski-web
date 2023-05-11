package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static it.klotski.web.game.constants.ApplicationConstants.DATE_FORMAT;

@Getter
@RequiredArgsConstructor
public class GameResponse {
    private final long id;
    private final int startConfigurationId;
    private final String date;
    private final long duration;
    private final boolean finished;
    private final Board board;

    public static GameResponse from(Game game, Board tiles) {
        return new GameResponse(game.getId(),
                game.getStartConfigurationId(),
                DATE_FORMAT.format(game.getCreatedAt()),
                game.getDuration(),
                game.isFinished(),
                tiles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameResponse that = (GameResponse) o;

        if (getId() != that.getId()) return false;
        if (getStartConfigurationId() != that.getStartConfigurationId()) return false;
        if (getDuration() != that.getDuration()) return false;
        if (isFinished() != that.isFinished()) return false;
        if (!getDate().equals(that.getDate())) return false;
        return getBoard().equals(that.getBoard());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getStartConfigurationId();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + (int) (getDuration() ^ (getDuration() >>> 32));
        result = 31 * result + (isFinished() ? 1 : 0);
        result = 31 * result + getBoard().hashCode();
        return result;
    }
}
