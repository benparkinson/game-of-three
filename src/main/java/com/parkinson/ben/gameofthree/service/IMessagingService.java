package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.model.GameMove;

public interface IMessagingService {

    void sendMoveByMe(GameMove gameMove);

    void sendMoveByOtherPlayer(GameMove gameMove);

}
