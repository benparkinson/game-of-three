package com.parkinson.ben.gameofthree.component.context;

import com.parkinson.ben.gameofthree.client.IOtherPlayerClient;
import com.parkinson.ben.gameofthree.client.impl.OtherPlayerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
public class ClientConfiguration {

    @Value("${other-player.uri}")
    private URI otherPlayerUri;

    @Bean
    public IOtherPlayerClient otherPlayerClient() {
        return new OtherPlayerClient(otherPlayerUri, new RestTemplate());
    }
}
