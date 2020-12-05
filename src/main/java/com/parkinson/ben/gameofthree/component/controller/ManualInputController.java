package com.parkinson.ben.gameofthree.component.controller;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.IGameService;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/gameofthree")
public class ManualInputController {

    private final IGameService gameService;
    private final IOtherPlayerService otherPlayerService;

    @Autowired
    public ManualInputController(IOtherPlayerService otherPlayerService, IGameService gameService) {
        this.otherPlayerService = otherPlayerService;
        this.gameService = gameService;
    }

    @PostMapping("/v1/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public GameMove startNewGame() {
        GameMove firstMove = gameService.startGameWithRandomMove();

        otherPlayerService.sendNextMove(firstMove);

        return firstMove;
    }

    @PostMapping("/v1/api/manual/gamemoves")
    @ResponseStatus(HttpStatus.CREATED)
    public void forwardManualGameMove(@Valid @RequestBody GameMove gameMove) {
        otherPlayerService.sendNextMove(gameMove);
    }
}
