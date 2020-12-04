package com.parkinson.ben.gameofthree.client;

import com.parkinson.ben.gameofthree.model.GameMove;

public interface IOtherPlayerClient {

    void sendNextMove(GameMove gameMove);

}
