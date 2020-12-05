package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.impl.GameService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class GameServiceTest {

    @Test
    public void testGameServiceCallsNextMoveWhenAutomatic() {
        IGameService gameService = new GameService(PlayMode.AUTOMATIC);
        GameMove mockMove = mock(GameMove.class);
        when(mockMove.makeNextMove()).thenReturn(Optional.of(new GameMove(19)));
        gameService.makeNextGameMove(mockMove);
        verify(mockMove, times(1)).makeNextMove();
    }

    @Test
    public void testGameServiceDoesntCallNextMoveWhenManual() {
        IGameService gameService = new GameService(PlayMode.MANUAL);
        GameMove mockMove = mock(GameMove.class);
        gameService.makeNextGameMove(mockMove);
        verify(mockMove, never()).makeNextMove();
    }

    @Test
    public void testGameServiceDoesntCallNextMoveWhenOtherPlayerWon() {
        IGameService gameService = new GameService(PlayMode.MANUAL);
        GameMove mockMove = mock(GameMove.class);
        when(mockMove.wasWinningMove()).thenReturn(true);
        gameService.makeNextGameMove(mockMove);
        verify(mockMove, never()).makeNextMove();
    }
}
