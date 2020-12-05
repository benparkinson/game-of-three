package com.parkinson.ben.gameofthree.component.context;

import com.parkinson.ben.gameofthree.client.IOtherPlayerClient;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.IGameService;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import com.parkinson.ben.gameofthree.service.impl.GameService;
import com.parkinson.ben.gameofthree.service.impl.OtherPlayerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameOfThreeContext {

    @Value("${game-of-three.play-mode}")
    private PlayMode playMode;

    @Value("${game-of-three.min-start-move}")
    private int minStartMove;

    @Value("${game-of-three.max-start-move}")
    private int maxStartMove;

    @Bean
    public IGameService gameService() {
        return new GameService(playMode, minStartMove, maxStartMove);
    }

    @Bean
    public IOtherPlayerService otherPlayerService(IOtherPlayerClient otherPlayerClient) {
        return new OtherPlayerService(otherPlayerClient);
    }
}
