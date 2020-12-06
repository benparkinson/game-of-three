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
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(9);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(9);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();

        gameMove = new GameMove(18);
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(6);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(0);
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(18);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(18);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();
    }

    @Test
    public void testGameMoveAddsOneWhenGivenANumberOneLessThanAMultipleOfThree() {
        GameMove gameMove = new GameMove(8);
        Optional<GameMove> gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(3);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(1);
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(9);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(8);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();

        gameMove = new GameMove(17);
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(6);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(1);
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(18);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(17);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();
    }

    @Test
    public void testGameMoveSubtractsOneWhenGivenANumberOneMoreThanAMultipleOfThree() {
        GameMove gameMove = new GameMove(10);
        Optional<GameMove> gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(3);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(-1);
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(9);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(10);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();

        gameMove = new GameMove(19);
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(6);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(-1);
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(18);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(19);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();
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
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(57);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(56);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();
        assertThat(gameMoveResult.get().wasWinningMove()).isFalse();

        gameMove = new GameMove(gameMoveResult.get().getResult());
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(6);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(-1);
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(18);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(19);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();
        assertThat(gameMoveResult.get().wasWinningMove()).isFalse();

        gameMove = new GameMove(gameMoveResult.get().getResult());
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(2);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(0);
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(6);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(6);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();
        assertThat(gameMoveResult.get().wasWinningMove()).isFalse();

        gameMove = new GameMove(gameMoveResult.get().getResult());
        gameMoveResult = gameMove.makeNextMove();
        assertThat(gameMoveResult).isPresent();
        assertThat(gameMoveResult.get().getResult()).isEqualTo(1);
        assertThat(gameMoveResult.get().getAddend()).isEqualTo(1);
        assertThat(gameMoveResult.get().getDividend()).isEqualTo(3);
        assertThat(gameMoveResult.get().getPreviousResult()).isEqualTo(2);
        assertThat(gameMoveResult.get().isFirstMove()).isFalse();
        assertThat(gameMoveResult.get().wasWinningMove()).isTrue();
    }

    @Test
    public void testGameMoveIsWinningWithResultOfOne() {
        GameMove gameMove = new GameMove(0, 1, 3, 1);
        AssertionsForClassTypes.assertThat(gameMove.wasWinningMove()).isTrue();
    }

    @Test
    public void testGameMoveIsNotWinningWithResultOtherThanOne() {
        GameMove gameMove = new GameMove(0, 1, 6, 2);
        AssertionsForClassTypes.assertThat(gameMove.wasWinningMove()).isFalse();

        gameMove = new GameMove(0, 1, 18, 6);
        AssertionsForClassTypes.assertThat(gameMove.wasWinningMove()).isFalse();

        gameMove = new GameMove(0, 1, 999, 333);
        AssertionsForClassTypes.assertThat(gameMove.wasWinningMove()).isFalse();
    }
}
