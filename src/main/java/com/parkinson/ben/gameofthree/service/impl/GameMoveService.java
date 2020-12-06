package com.parkinson.ben.gameofthree.service.impl;

import com.parkinson.ben.gameofthree.exception.GameMoveException;
import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.IGameMoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

public class GameMoveService implements IGameMoveService {

    private final int minStartMove;
    private final int maxStartMove;
    private final Random random;

    public GameMoveService(int minStartMove, int maxStartMove) {
        this.minStartMove = minStartMove;
        this.maxStartMove = maxStartMove;
        this.random = new Random(new Date().getTime());
    }

    @Override
    public GameMove createRandomFirstGameMove() {
        int firstMove = minStartMove + random.nextInt(maxStartMove);
        return new GameMove(firstMove);
    }

    @Override
    public Optional<GameMove> makeNextGameMove(GameMove gameMove) {
        if (gameMove.wasWinningMove()) {
            return Optional.empty();
        }

        return Optional.of(
                gameMove.makeNextMove().orElseThrow(() -> new GameMoveException(gameMove))
        );
    }
}
