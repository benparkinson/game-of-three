package com.parkinson.ben.gameofthree.component.controller;

import com.parkinson.ben.gameofthree.model.GameMove;
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
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    private final IGameService gameService;

    @Autowired
    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/v1/api/game-moves")
    @ResponseStatus(HttpStatus.CREATED)
    public void handleGameMove(@Valid @RequestBody GameMove gameMove) {
        logger.info(String.format("Received game move: %s", gameMove));
        gameService.handleOtherPlayerGameMove(gameMove);
    }

    @GetMapping("/v1/api/play-mode")
    @ResponseStatus(HttpStatus.OK)
    public PlayMode findPlayMode() {
        PlayMode playMode = gameService.getPlayMode();
        logger.info(String.format("Received request for play mode, configured mode: %s", playMode));
        return playMode;
    }
}
