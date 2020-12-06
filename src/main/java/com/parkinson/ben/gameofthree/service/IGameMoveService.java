package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.model.GameMove;

import java.util.Optional;

public interface IGameMoveService {

    GameMove createRandomFirstGameMove();

    Optional<GameMove> makeNextGameMove(GameMove gameMove);

}
