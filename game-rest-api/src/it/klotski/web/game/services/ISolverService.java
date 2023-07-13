package it.klotski.web.game.services;

import it.klotski.web.game.payload.reponses.SolveResponse;
import it.klotski.web.game.payload.requests.SolveRequest;

public interface ISolverService {

    SolveResponse nextBestMove(SolveRequest solveRequest);
}
