package com.parkinson.ben.gameofthree.service;

import com.parkinson.ben.gameofthree.exception.GameMoveException;
import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.ManualGameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import com.parkinson.ben.gameofthree.service.impl.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private IGameMoveService gameMoveService;
    private IMessagingService messagingService;
    private IOtherPlayerService otherPlayerService;

    @BeforeEach
    public void setUp() {
        gameMoveService = mock(IGameMoveService.class);
        messagingService = mock(IMessagingService.class);
        otherPlayerService = mock(IOtherPlayerService.class);
    }

    @Test
    public void testGameServiceCallsNextMoveWhenAutomatic() {
        IGameService gameService = makeGameService(PlayMode.AUTOMATIC);
        GameMove mockMove = mock(GameMove.class);
        GameMove nextMove = new GameMove(10, -1, 9, 3);
        when(gameMoveService.makeNextGameMove(any())).thenReturn(Optional.of(nextMove));
        when(mockMove.wasWinningMove()).thenReturn(false);
        gameService.handleOtherPlayerGameMove(mockMove);
        verify(mockMove, never()).makeNextMove();
        verify(messagingService, times(1)).sendMoveByOtherPlayer(eq(mockMove));
        verify(gameMoveService, times(1)).makeNextGameMove(eq(mockMove));
        verify(messagingService, times(1)).sendMoveByMe(eq(nextMove));
        verify(otherPlayerService, times(1)).sendNextMove(eq(nextMove));
    }

    @Test
    public void testGameServiceDoesntForwardNextMoveWhenNothingReturnedFromGameMoveService() {
        IGameService gameService = makeGameService(PlayMode.AUTOMATIC);
        GameMove mockMove = mock(GameMove.class);
        when(gameMoveService.makeNextGameMove(any())).thenReturn(Optional.empty());
        when(mockMove.wasWinningMove()).thenReturn(false);
        gameService.handleOtherPlayerGameMove(mockMove);
        verify(mockMove, never()).makeNextMove();
        verify(messagingService, times(1)).sendMoveByOtherPlayer(eq(mockMove));
        verify(gameMoveService, times(1)).makeNextGameMove(eq(mockMove));
        verify(messagingService, never()).sendMoveByMe(any());
        verify(otherPlayerService, never()).sendNextMove(any());
    }

    @Test
    public void testGameServiceDoesntCallNextMoveWhenManual() {
        IGameService gameService = makeGameService(PlayMode.MANUAL);
        GameMove mockMove = mock(GameMove.class);
        gameService.handleOtherPlayerGameMove(mockMove);
        verify(mockMove, never()).makeNextMove();
        verify(messagingService, times(1)).sendMoveByOtherPlayer(eq(mockMove));
        verify(gameMoveService, never()).makeNextGameMove(any());
        verify(messagingService, never()).sendMoveByMe(any());
        verify(otherPlayerService, never()).sendNextMove(any());
    }

    @Test
    public void testGameServiceStartGameCallsOtherServices() {
        IGameService gameService = makeGameService(PlayMode.MANUAL);
        GameMove mockMove = mock(GameMove.class);
        when(gameMoveService.createRandomFirstGameMove()).thenReturn(mockMove);
        gameService.startNewGame();
        verify(messagingService, times(1)).sendMoveByMe(eq(mockMove));
        verify(otherPlayerService, times(1)).sendNextMove(eq(mockMove));
    }

    @Test
    public void testGameServiceForwardMoveCallsOtherServices() {
        IGameService gameService = makeGameService(PlayMode.MANUAL);
        ManualGameMove mockManualMove = mock(ManualGameMove.class);
        GameMove convertedMove = mock(GameMove.class);
        when(mockManualMove.convertToGameMove()).thenReturn(convertedMove);
        gameService.forwardManualMove(mockManualMove);
        verify(messagingService, times(1)).sendMoveByMe(eq(convertedMove));
        verify(otherPlayerService, times(1)).sendNextMove(eq(convertedMove));
    }

    @Test
    public void testGameServiceGetPlayMode() {
        IGameService gameService = makeGameService(PlayMode.AUTOMATIC);
        assertThat(gameService.getPlayMode()).isEqualTo(PlayMode.AUTOMATIC);

        gameService = makeGameService(PlayMode.MANUAL);
        assertThat(gameService.getPlayMode()).isEqualTo(PlayMode.MANUAL);
    }

    private IGameService makeGameService(PlayMode playMode) {
        return new GameService(gameMoveService, otherPlayerService, messagingService, playMode);
    }
}
