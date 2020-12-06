package com.parkinson.ben.gameofthree.component.controller;

import com.parkinson.ben.gameofthree.model.ManualGameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/gameofthree")
public class ManualInputController {

    private final IGameService gameService;

    @Autowired
    public ManualInputController(IGameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/v1/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public void startNewGame() {
        gameService.startNewGame();
    }

    @PostMapping("/v1/api/manual/gamemoves")
    @ResponseStatus(HttpStatus.CREATED)
    public void forwardManualGameMove(@Valid @RequestBody ManualGameMove manualGameMove) {
        gameService.forwardManualMove(manualGameMove);
    }

    @GetMapping("/v1/api/playmode")
    @ResponseStatus(HttpStatus.OK)
    public PlayMode findPlayMode() {
        return gameService.getPlayMode();
    }
}
