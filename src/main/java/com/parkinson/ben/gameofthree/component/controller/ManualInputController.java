package com.parkinson.ben.gameofthree.component.controller;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.ManualGameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.IGameService;
import com.parkinson.ben.gameofthree.service.IMessagingService;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/gameofthree")
public class ManualInputController {

    private final IGameService gameService;
    private final IOtherPlayerService otherPlayerService;
    private final IMessagingService messagingService;

    @Autowired
    public ManualInputController(IOtherPlayerService otherPlayerService, IGameService gameService,
                                 IMessagingService messagingService) {
        this.otherPlayerService = otherPlayerService;
        this.gameService = gameService;
        this.messagingService = messagingService;
    }

    @PostMapping("/v1/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public GameMove startNewGame() {
        GameMove firstMove = gameService.startGameWithRandomMove();

        messagingService.sendMoveByMe(firstMove);

        otherPlayerService.sendNextMove(firstMove);

        return firstMove;
    }

    @PostMapping("/v1/api/manual/gamemoves")
    @ResponseStatus(HttpStatus.CREATED)
    public void forwardManualGameMove(@Valid @RequestBody ManualGameMove manualGameMove) {
        GameMove gameMove = manualGameMove.convertToGameMove();
        messagingService.sendMoveByMe(gameMove);
        otherPlayerService.sendNextMove(gameMove);
    }

    @GetMapping("/v1/api/playmode")
    @ResponseStatus(HttpStatus.OK)
    public PlayMode findPlayMode() {
        return gameService.getPlayMode();
    }
}
