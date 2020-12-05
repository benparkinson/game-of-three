package com.parkinson.ben.gameofthree.service.impl;

import com.parkinson.ben.gameofthree.exception.GameMoveException;
import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.IGameService;

import java.util.Optional;

public class GameService implements IGameService {

    private final PlayMode playMode;

    public GameService(PlayMode playMode) {
        this.playMode = playMode;
    }

    @Override
    public Optional<GameMove> makeNextGameMove(GameMove gameMove) {
        if (gameMove.wasWinningMove()) {
            System.out.println("I lost :(");
            return Optional.empty();
        }

        if (playMode == PlayMode.AUTOMATIC) {
            return Optional.of(
                    gameMove.makeNextMove().orElseThrow(() -> new GameMoveException(gameMove))
            );
        }
        return Optional.empty();
    }
}
