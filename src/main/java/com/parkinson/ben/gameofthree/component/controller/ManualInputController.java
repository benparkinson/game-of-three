package com.parkinson.ben.gameofthree.component.controller;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.IGameService;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void startNewGame() {
        GameMove firstMove = gameService.startGameWithRandomMove();

        otherPlayerService.sendNextMove(firstMove);
    }
}
