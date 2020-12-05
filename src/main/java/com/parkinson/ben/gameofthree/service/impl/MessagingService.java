package com.parkinson.ben.gameofthree.service.impl;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.IMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class MessagingService implements IMessagingService {

    private static final String TOPIC_MY_MOVES = "/topic/my-moves";
    private static final String TOPIC_THEIR_MOVES = "/topic/their-moves";

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessagingService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendMoveByMe(GameMove gameMove) {
        messagingTemplate.convertAndSend(TOPIC_MY_MOVES, gameMove);
    }

    @Override
    public void sendMoveByOtherPlayer(GameMove gameMove) {
        messagingTemplate.convertAndSend(TOPIC_THEIR_MOVES, gameMove);
    }
}
