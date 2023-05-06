package it.klotski.web.game.payload.reponses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CsrfTokenResponse {
    private final String parameterName;
    private final String token;
    private final String headerName;
}
