package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.model.GameMove;

public interface IGameService {

    GameMove makeNextGameMove(GameMove gameMove);

}
