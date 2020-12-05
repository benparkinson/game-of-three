package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.model.GameMove;

import java.util.Optional;

public interface IGameService {

    Optional<GameMove> makeNextGameMove(GameMove gameMove);

}
