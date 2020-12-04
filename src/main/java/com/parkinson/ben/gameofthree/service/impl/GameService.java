package com.parkinson.ben.gameofthree.service.impl;

import com.parkinson.ben.gameofthree.exception.GameMoveException;
import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.IGameService;

public class GameService implements IGameService {

    @Override
    public GameMove makeNextGameMove(GameMove gameMove) {
        return gameMove.makeNextMove().orElseThrow(() -> new GameMoveException(gameMove));
    }
}
