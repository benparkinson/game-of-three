package com.parkinson.ben.gameofthree.component.controller;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.IGameService;
import com.parkinson.ben.gameofthree.service.IMessagingService;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/gameofthree")
public class GameController {

    private final IGameService gameService;
    private final IOtherPlayerService otherPlayerService;
    private final IMessagingService messagingService;

    @Autowired
    public GameController(IGameService gameService, IOtherPlayerService otherPlayerService,
                          IMessagingService messagingService) {
        this.gameService = gameService;
        this.otherPlayerService = otherPlayerService;
        this.messagingService = messagingService;
    }

    @PostMapping("/v1/api/gamemoves")
    @ResponseStatus(HttpStatus.CREATED)
    public void handleGameMove(@Valid @RequestBody GameMove gameMove) {
        messagingService.sendMoveByOtherPlayer(gameMove);
        Optional<GameMove> nextMove = gameService.makeNextGameMove(gameMove);
        nextMove.ifPresent(myMove -> {
            messagingService.sendMoveByMe(myMove);
            otherPlayerService.sendNextMove(myMove);
        });
    }
}
