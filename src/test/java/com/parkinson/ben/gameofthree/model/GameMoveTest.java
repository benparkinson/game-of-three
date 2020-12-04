package com.parkinson.ben.gameofthree.model;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GameMoveTest {

    @Test
    public void testGameMoveDividesByThreeWhenGivenAMultipleOfThree() {
        GameMove gameMove = new GameMove(9);
        Optional<GameMove> gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(3);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(0);

        gameMove = new GameMove(18);
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(6);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(0);
    }

    @Test
    public void testGameMoveAddsOneWhenGivenANumberOneLessThanAMultipleOfThree() {
        GameMove gameMove = new GameMove(8);
        Optional<GameMove> gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(3);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(1);

        gameMove = new GameMove(17);
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(6);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(1);
    }

    @Test
    public void testGameMoveSubtractsOneWhenGivenANumberOneMoreThanAMultipleOfThree() {
        GameMove gameMove = new GameMove(10);
        Optional<GameMove> gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(3);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(-1);

        gameMove = new GameMove(19);
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(6);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(-1);
    }

    @Test
    public void testGameMoveReturnsEmptyWhenGivenZero() {
        GameMove gameMove = new GameMove(0);
        Optional<GameMove> gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isEmpty();
    }

    @Test
    public void testGameMoveChainCanWin() {
        GameMove gameMove = new GameMove(56);
        Optional<GameMove> gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(19);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(1);
        assertThat(gameMoveResult.get().wasWinningMove()).isFalse();

        gameMove = new GameMove(gameMoveResult.get().getResult());
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(6);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(-1);
        assertThat(gameMoveResult.get().wasWinningMove()).isFalse();

        gameMove = new GameMove(gameMoveResult.get().getResult());
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(2);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(0);
        assertThat(gameMoveResult.get().wasWinningMove()).isFalse();

        gameMove = new GameMove(gameMoveResult.get().getResult());
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(1);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(1);
        assertThat(gameMoveResult.get().wasWinningMove()).isTrue();
    }

    @Test
    public void testGameMoveIsWinningWithResultOfOne() {
        GameMove gameMove = new GameMove(1, 1);
        AssertionsForClassTypes.assertThat(gameMove.wasWinningMove()).isTrue();
    }

    @Test
    public void testGameMoveIsNotWinningWithResultOtherThanOne() {
        GameMove gameMove = new GameMove(2, 1);
        AssertionsForClassTypes.assertThat(gameMove.wasWinningMove()).isFalse();

        gameMove = new GameMove(6, 1);
        AssertionsForClassTypes.assertThat(gameMove.wasWinningMove()).isFalse();

        gameMove = new GameMove(453, 1);
        AssertionsForClassTypes.assertThat(gameMove.wasWinningMove()).isFalse();
    }
}
