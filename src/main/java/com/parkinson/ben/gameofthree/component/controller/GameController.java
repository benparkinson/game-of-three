package com.parkinson.ben.gameofthree.component.controller;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.IGameService;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        System.out.println("Received game move: "+ gameMove.toString());

        if (gameMove.wasWinningMove()) {
            System.out.println("I lost :(");
        } else {
            GameMove nextMove = gameService.makeNextGameMove(gameMove);

            System.out.println("Responding with next move: "+nextMove.toString());

            if (nextMove.wasWinningMove()) {
                System.out.println("I won :)");
            }

            otherPlayerService.sendNextMove(nextMove);
        }
    }
}
