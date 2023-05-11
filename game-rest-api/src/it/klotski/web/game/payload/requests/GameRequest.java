package it.klotski.web.game.payload.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameRequest {
    private final int startConfigId;
}
