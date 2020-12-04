package com.parkinson.ben.gameofthree.component.controller;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gameofthree")
public class ManualInputController {
    private final IOtherPlayerService otherPlayerService;

    @Autowired
    public ManualInputController(IOtherPlayerService otherPlayerService) {
        this.otherPlayerService = otherPlayerService;
    }

    // todo should incept a random number rather than have it passed in
    @PostMapping("/v1/api/games")
    public void startNewGame(@RequestParam int initialValue) {
        GameMove gameMove = new GameMove(initialValue);

        otherPlayerService.sendNextMove(gameMove);
    }
}
