package com.parkinson.ben.gameofthree.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ManualGameMoveTest {

    @Test
    public void testConvertToGameMove() {
        ManualGameMove manualGameMove = new ManualGameMove(17, 1);
        GameMove gameMove = manualGameMove.convertToGameMove();
        assertThat(gameMove.getPreviousResult()).isEqualTo(17);
        assertThat(gameMove.getAddend()).isEqualTo(1);
        assertThat(gameMove.getResult()).isEqualTo(6);
        assertThat(gameMove.getDividend()).isEqualTo(18);
        assertThat(gameMove.wasWinningMove()).isFalse();
    }

    @Test
    public void testConvertToGameMoveWinningMove() {
        ManualGameMove manualGameMove = new ManualGameMove(2, 1);
        GameMove gameMove = manualGameMove.convertToGameMove();
        assertThat(gameMove.getPreviousResult()).isEqualTo(2);
        assertThat(gameMove.getAddend()).isEqualTo(1);
        assertThat(gameMove.getResult()).isEqualTo(1);
        assertThat(gameMove.getDividend()).isEqualTo(3);
        assertThat(gameMove.wasWinningMove()).isTrue();
    }

}
