package it.klotski.web.game.payload.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.klotski.web.game.domain.Direction;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.util.DigestUtils;

@EqualsAndHashCode
@RequiredArgsConstructor
public class BoardRequest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final TileRequest[][] tiles;

    public boolean isValid(MoveRequest moveRequest) {
        Direction direction = moveRequest.getDirection();
        for (int i = 0; i < moveRequest.getTile().width(); i++) {
            for (int j = 0; j < moveRequest.getTile().height(); j++) {
                int calculatedX = i + moveRequest.getTile().x() + direction.getX();
                int calculatedY = i + moveRequest.getTile().y() + direction.getY();
                if (isOccupied(calculatedX, calculatedY, moveRequest.getTile())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOccupied(int x, int y, TileRequest tileToMove) {
        if (x >= tiles[0].length) {
            return true;
        }
        if (y >= tiles.length) {
            return true;
        }

        TileRequest tile = tiles[y][x];

        return tile != null && !tile.equals(tileToMove);
    }

    public String getBoardHash() throws JsonProcessingException {
        return DigestUtils.md5DigestAsHex(OBJECT_MAPPER.writeValueAsBytes(this));
    }
}
