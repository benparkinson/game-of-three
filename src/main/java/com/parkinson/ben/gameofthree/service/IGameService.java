package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;

import java.util.Optional;

public interface IGameService {

    GameMove startGameWithRandomMove();

    Optional<GameMove> makeNextGameMove(GameMove gameMove);

    PlayMode getPlayMode();

}
