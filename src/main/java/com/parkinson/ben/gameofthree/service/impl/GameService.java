package com.parkinson.ben.gameofthree.service.impl;

import com.parkinson.ben.gameofthree.exception.GameMoveException;
import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.IGameService;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

public class GameService implements IGameService {

    private final PlayMode playMode;
    private final int minStartMove;
    private final int maxStartMove;
    private final Random random;

    public GameService(PlayMode playMode, int minStartMove, int maxStartMove) {
        this.playMode = playMode;
        this.minStartMove = minStartMove;
        this.maxStartMove = maxStartMove;
        this.random = new Random(new Date().getTime());
    }

    @Override
    public GameMove startGameWithRandomMove() {
        int firstMove = minStartMove + random.nextInt(maxStartMove);
        return new GameMove(firstMove);
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
