package com.parkinson.ben.gameofthree.service.impl;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.ManualGameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.IGameMoveService;
import com.parkinson.ben.gameofthree.service.IGameService;
import com.parkinson.ben.gameofthree.service.IMessagingService;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class GameService implements IGameService {

    private final PlayMode playMode;
    private final IGameMoveService gameMoveService;
    private final IOtherPlayerService otherPlayerService;
    private final IMessagingService messagingService;

    @Autowired
    public GameService(IGameMoveService gameMoveService, IOtherPlayerService otherPlayerService,
                       IMessagingService messagingService, PlayMode playMode) {
        this.gameMoveService = gameMoveService;
        this.otherPlayerService = otherPlayerService;
        this.messagingService = messagingService;
        this.playMode = playMode;
    }

    @Override
    public void startNewGame() {
        GameMove firstMove = gameMoveService.createRandomFirstGameMove();

        messagingService.sendMoveByMe(firstMove);
        otherPlayerService.sendNextMove(firstMove);
    }

    @Override
    public void handleOtherPlayerGameMove(GameMove gameMove) {
        messagingService.sendMoveByOtherPlayer(gameMove);

        if (playMode == PlayMode.AUTOMATIC) {
            sendNextGameMove(gameMove);
        }
    }

    private void sendNextGameMove(GameMove gameMove) {
        Optional<GameMove> nextMove = gameMoveService.makeNextGameMove(gameMove);
        nextMove.ifPresent(myMove -> {
            messagingService.sendMoveByMe(myMove);
            otherPlayerService.sendNextMove(myMove);
        });
    }

    @Override
    public void forwardManualMove(ManualGameMove manualGameMove) {
        GameMove gameMove = manualGameMove.convertToGameMove();
        messagingService.sendMoveByMe(gameMove);
        otherPlayerService.sendNextMove(gameMove);
    }

    @Override
    public PlayMode getPlayMode() {
        return playMode;
    }
}
