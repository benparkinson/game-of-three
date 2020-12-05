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
public class GameController {

    private final IGameService gameService;
    private final IOtherPlayerService otherPlayerService;

    @Autowired
    public GameController(IGameService gameService, IOtherPlayerService otherPlayerService) {
        this.gameService = gameService;
        this.otherPlayerService = otherPlayerService;
    }

    @PostMapping("/v1/api/gamemoves")
    @ResponseStatus(HttpStatus.CREATED)
    public void handleGameMove(@Valid @RequestBody GameMove gameMove) {
        Optional<GameMove> nextMove = gameService.makeNextGameMove(gameMove);
        nextMove.ifPresent(otherPlayerService::sendNextMove);
    }
}
