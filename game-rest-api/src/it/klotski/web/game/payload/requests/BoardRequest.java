package it.klotski.web.game.payload.requests;

import com.google.gson.Gson;
import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Getter
@RequiredArgsConstructor
public class BoardRequest {
    private static final Gson GSON = new Gson();
    private final ITile[][] tiles;

    public String getBoardHash() {
        return DigestUtils.md5DigestAsHex(GSON.toJson(this).getBytes(StandardCharsets.UTF_8));
    }
}
