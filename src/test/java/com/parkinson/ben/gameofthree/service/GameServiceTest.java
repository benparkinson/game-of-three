package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.impl.GameService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private static final int MIN_START_MOVE = 2;
    private static final int MAX_START_MOVE = 1000;

    @Test
    public void testGameServiceCallsNextMoveWhenAutomatic() {
        IGameService gameService = makeGameService(PlayMode.AUTOMATIC);
        GameMove mockMove = mock(GameMove.class);
        when(mockMove.makeNextMove()).thenReturn(Optional.of(new GameMove(19)));
        gameService.makeNextGameMove(mockMove);
        verify(mockMove, times(1)).makeNextMove();
    }

    @Test
    public void testGameServiceDoesntCallNextMoveWhenManual() {
        IGameService gameService = makeGameService(PlayMode.MANUAL);
        GameMove mockMove = mock(GameMove.class);
        gameService.makeNextGameMove(mockMove);
        verify(mockMove, never()).makeNextMove();
    }

    @Test
    public void testGameServiceDoesntCallNextMoveWhenOtherPlayerWon() {
        IGameService gameService = makeGameService(PlayMode.MANUAL);
        GameMove mockMove = mock(GameMove.class);
        when(mockMove.wasWinningMove()).thenReturn(true);
        gameService.makeNextGameMove(mockMove);
        verify(mockMove, never()).makeNextMove();
    }

    @Test
    public void testGameServiceStartsGameWithinBounds() {
        IGameService gameService = makeGameService(PlayMode.AUTOMATIC);
        GameMove gameMove = gameService.startGameWithRandomMove();
        assertThat(gameMove.getAddend()).isEqualTo(0);
        assertThat(gameMove.getResult()).isGreaterThanOrEqualTo(MIN_START_MOVE);
        assertThat(gameMove.getResult()).isLessThanOrEqualTo(MAX_START_MOVE);
    }

    private IGameService makeGameService(PlayMode playMode) {
        return new GameService(playMode, MIN_START_MOVE, MAX_START_MOVE);
    }
}
