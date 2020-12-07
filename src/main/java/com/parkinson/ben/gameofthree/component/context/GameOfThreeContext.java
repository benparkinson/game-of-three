package com.parkinson.ben.gameofthree.component.context;

import com.parkinson.ben.gameofthree.client.IOtherPlayerClient;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.IGameMoveService;
import com.parkinson.ben.gameofthree.service.IGameService;
import com.parkinson.ben.gameofthree.service.IMessagingService;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import com.parkinson.ben.gameofthree.service.impl.GameMoveService;
import com.parkinson.ben.gameofthree.service.impl.GameService;
import com.parkinson.ben.gameofthree.service.impl.MessagingService;
import com.parkinson.ben.gameofthree.service.impl.OtherPlayerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class GameOfThreeContext {

    @Value("${game-of-three.play-mode}")
    private PlayMode playMode;

    @Value("${game-of-three.min-start-move}")
    private int minStartMove;

    @Value("${game-of-three.max-start-move}")
    private int maxStartMove;

    @Bean
    public IGameMoveService gameMoveService() {
        return new GameMoveService(minStartMove, maxStartMove);
    }

    @Bean
    public IOtherPlayerService otherPlayerService(IOtherPlayerClient otherPlayerClient) {
        return new OtherPlayerService(otherPlayerClient);
    }

    @Bean
    public IMessagingService messagingService(SimpMessagingTemplate simpMessagingTemplate) {
        return new MessagingService(simpMessagingTemplate);
    }

    @Bean
    public IGameService gameService(IGameMoveService gameMoveService, IOtherPlayerService otherPlayerService,
                                    IMessagingService messagingService) {
        return new GameService(gameMoveService, otherPlayerService, messagingService, playMode);
    }
}
