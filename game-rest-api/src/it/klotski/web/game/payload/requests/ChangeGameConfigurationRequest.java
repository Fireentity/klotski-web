package it.klotski.web.game.payload.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChangeGameConfigurationRequest {
    private final long gameId;
    private final int startConfigurationId;
}
