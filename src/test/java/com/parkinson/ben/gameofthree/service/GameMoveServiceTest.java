package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.exception.GameMoveException;
import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.service.impl.GameMoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GameMoveServiceTest {

    private static final int MIN_START_MOVE = 2;
    private static final int MAX_START_MOVE = 1000;

    private IGameMoveService gameMoveService;

    @BeforeEach
    public void setUp() {
        gameMoveService = new GameMoveService(MIN_START_MOVE, MAX_START_MOVE);
    }

    @Test
    public void testGameMoveServiceCallsNextMove() {
        GameMove mockMove = mock(GameMove.class);
        when(mockMove.makeNextMove()).thenReturn(Optional.of(new GameMove(19)));
        gameMoveService.makeNextGameMove(mockMove);
        verify(mockMove, times(1)).makeNextMove();
    }

    @Test
    public void testGameMoveServiceDoesntCallNextMoveWhenOtherPlayerWon() {
        GameMove mockMove = mock(GameMove.class);
        when(mockMove.wasWinningMove()).thenReturn(true);
        gameMoveService.makeNextGameMove(mockMove);
        verify(mockMove, never()).makeNextMove();
    }

    @Test
    public void testGameMoveServiceStartsGameWithinBounds() {
        GameMove gameMove = gameMoveService.createRandomFirstGameMove();
        assertThat(gameMove.getAddend()).isEqualTo(0);
        assertThat(gameMove.getResult()).isGreaterThanOrEqualTo(MIN_START_MOVE);
        assertThat(gameMove.getResult()).isLessThanOrEqualTo(MAX_START_MOVE);
    }

    @Test
    public void testGameServiceThrowsWhenNoMovePossible() {
        GameMove mockMove = mock(GameMove.class);
        when(mockMove.wasWinningMove()).thenReturn(false);
        when(mockMove.makeNextMove()).thenReturn(Optional.empty());
        assertThrows(GameMoveException.class, () -> {
            gameMoveService.makeNextGameMove(mockMove);
        });
    }
}
