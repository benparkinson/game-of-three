package com.parkinson.ben.gameofthree.component.controller;

import com.parkinson.ben.gameofthree.model.ManualGameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.IGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/game-of-three")
public class ManualInputController {

    private static final Logger logger = LoggerFactory.getLogger(ManualInputController.class);

    private final IGameService gameService;

    @Autowired
    public ManualInputController(IGameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/v1/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public void startNewGame() {
        logger.info("Starting new game...");
        gameService.startNewGame();
    }

    @PostMapping("/v1/api/manual/game-moves")
    @ResponseStatus(HttpStatus.CREATED)
    public void forwardManualGameMove(@Valid @RequestBody ManualGameMove manualGameMove) {
        logger.info(String.format("Received manual move: %s", manualGameMove));
        gameService.forwardManualMove(manualGameMove);
    }
}
