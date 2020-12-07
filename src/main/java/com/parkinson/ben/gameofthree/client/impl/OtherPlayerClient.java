package com.parkinson.ben.gameofthree.client.impl;

import com.parkinson.ben.gameofthree.client.IOtherPlayerClient;
import com.parkinson.ben.gameofthree.model.GameMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class OtherPlayerClient implements IOtherPlayerClient {

    private final URI otherPlayerUri;
    private final RestTemplate restTemplate;

    @Autowired
    public OtherPlayerClient(URI otherPlayerUri, RestTemplate restTemplate) {
        this.otherPlayerUri = otherPlayerUri;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendNextMove(GameMove gameMove) {
        URI uri = UriComponentsBuilder.fromUri(otherPlayerUri)
                .path("/game-of-three/v1/api/game-moves")
                .build()
                .encode()
                .toUri();

        restTemplate.postForLocation(uri, gameMove);
    }
}
