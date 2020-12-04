package com.parkinson.ben.gameofthree.service.impl;

import com.parkinson.ben.gameofthree.client.IOtherPlayerClient;
import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import org.springframework.beans.factory.annotation.Autowired;

public class OtherPlayerService implements IOtherPlayerService {

    private final IOtherPlayerClient otherPlayerClient;

    @Autowired
    public OtherPlayerService(IOtherPlayerClient otherPlayerClient) {
        this.otherPlayerClient = otherPlayerClient;
    }

    @Override
    public void sendNextMove(GameMove gameMove) {
        otherPlayerClient.sendNextMove(gameMove);
    }
}
