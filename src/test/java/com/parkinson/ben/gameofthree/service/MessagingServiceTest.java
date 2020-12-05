package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.impl.MessagingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

public class MessagingServiceTest {

    private static final String EXPECTED_TOPIC_MY_MOVES = "/topic/my-moves";
    private static final String EXPECTED_TOPIC_THEIR_MOVES = "/topic/their-moves";

    private IMessagingService messagingService;
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    public void setUp() {
        this.messagingTemplate = mock(SimpMessagingTemplate.class);
        this.messagingService = new MessagingService(messagingTemplate);
    }

    @Test
    public void testMessagingServiceCallsTemplateForMyMoves() {
        GameMove gameMove = new GameMove(20);
        messagingService.sendMoveByMe(gameMove);
        verify(messagingTemplate, times(1)).convertAndSend(eq(EXPECTED_TOPIC_MY_MOVES), eq(gameMove));
    }

    @Test
    public void testMessagingServiceCallsTemplateForTheirMoves() {
        GameMove gameMove = new GameMove(20);
        messagingService.sendMoveByOtherPlayer(gameMove);
        verify(messagingTemplate, times(1)).convertAndSend(eq(EXPECTED_TOPIC_THEIR_MOVES), eq(gameMove));
    }
}
