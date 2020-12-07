package com.parkinson.ben.gameofthree.exception;

import com.parkinson.ben.gameofthree.model.GameMove;

public class GameMoveException extends RuntimeException {

    public GameMoveException(GameMove previousGameMove) {
        super(String.format("Game unable to continue! Last game move: %s",
                previousGameMove.toString()));
    }
}
