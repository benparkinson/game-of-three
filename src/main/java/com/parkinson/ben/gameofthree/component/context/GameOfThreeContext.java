package com.parkinson.ben.gameofthree.component.context;

import com.parkinson.ben.gameofthree.client.IOtherPlayerClient;
import com.parkinson.ben.gameofthree.service.IGameService;
import com.parkinson.ben.gameofthree.service.IOtherPlayerService;
import com.parkinson.ben.gameofthree.service.impl.GameService;
import com.parkinson.ben.gameofthree.service.impl.OtherPlayerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameOfThreeContext {

    @Bean
    public IGameService gameService() {
        return new GameService();
    }

    @Bean
    public IOtherPlayerService otherPlayerService(IOtherPlayerClient otherPlayerClient) {
        return new OtherPlayerService(otherPlayerClient);
    }
}
