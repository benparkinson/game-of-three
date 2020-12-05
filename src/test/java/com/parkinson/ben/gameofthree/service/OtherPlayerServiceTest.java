package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.client.IOtherPlayerClient;
import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.impl.OtherPlayerService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class OtherPlayerServiceTest {

    @Test
    public void testServiceCallsClient() {
        IOtherPlayerClient mockClient = mock(IOtherPlayerClient.class);
        IOtherPlayerService otherPlayerService = new OtherPlayerService(mockClient);
        GameMove nextMove = new GameMove(9, 1);

        otherPlayerService.sendNextMove(nextMove);
        verify(mockClient, times(1)).sendNextMove(eq(nextMove));
    }

}
