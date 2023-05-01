package it.klotski.web.game.services;

import it.klotski.web.game.payload.requests.RegisterRequest;

public interface IUserService {

    void createUser(RegisterRequest registerRequest);
}
