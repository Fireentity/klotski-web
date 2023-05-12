package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.domain.game.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static it.klotski.web.game.constants.ApplicationConstants.DATE_FORMAT;

@Getter
@RequiredArgsConstructor
public class GameInfoResponse {
    private final long id;
    private final int startConfigurationId;
    private final String date;
    private final int moves;
    private final long duration;
    private final boolean finished;

    public static GameInfoResponse from(Game game) {
        return new GameInfoResponse(game.getId(),
                game.getStartConfigurationId(),
                DATE_FORMAT.format(game.getCreatedAt()),
                game.getMoves(),
                game.getDuration(),
                game.isFinished());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameInfoResponse that = (GameInfoResponse) o;

        if (getId() != that.getId()) return false;
        if (getStartConfigurationId() != that.getStartConfigurationId()) return false;
        if (getDuration() != that.getDuration()) return false;
        if (isFinished() != that.isFinished()) return false;
        return getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getStartConfigurationId();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + (int) (getDuration() ^ (getDuration() >>> 32));
        result = 31 * result + (isFinished() ? 1 : 0);
        return result;
    }
}
