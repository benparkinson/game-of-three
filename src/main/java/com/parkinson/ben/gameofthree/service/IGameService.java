package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.ManualGameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;

public interface IGameService {

    void startNewGame();

    void handleOtherPlayerGameMove(GameMove gameMove);

    void forwardManualMove(ManualGameMove manualGameMove);

    PlayMode getPlayMode();

    void updatePlayMode(PlayMode playMode);
}
