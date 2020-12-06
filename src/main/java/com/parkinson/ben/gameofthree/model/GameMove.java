package com.parkinson.ben.gameofthree.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

public class GameMove {

    private final int previousResult;
    @Min(-1)
    @Max(1)
    private final int addend;
    private final int dividend;
    private final int result;

    private final boolean firstMove;

    public GameMove() {
        this(0, 0, 0, 0, false);
    }

    public GameMove(int result) {
        this(0, 0, 0, result, true);
    }

    public GameMove(int previousResult, int addend, int dividend, int result) {
        this(previousResult, addend, dividend, result, false);
    }

    private GameMove(int previousResult, int addend, int dividend, int result, boolean firstMove) {
        this.previousResult = previousResult;
        this.addend = addend;
        this.dividend = dividend;
        this.result = result;
        this.firstMove = firstMove;
    }

    public Optional<GameMove> makeNextMove() {
        if (result == 0) {
            return Optional.empty();
        }

        return attemptMove(0).or(() -> attemptMove(1)).or(() -> attemptMove(-1));
    }

    public boolean wasWinningMove() {
        return result == 1;
    }

    public int getResult() {
        return result;
    }

    public int getAddend() {
        return addend;
    }

    public int getDividend() {
        return dividend;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public int getPreviousResult() {
        return previousResult;
    }

    @Override
    public String toString() {
        return "GameMove{" +
                "previousResult=" + previousResult +
                ", addend=" + addend +
                ", dividend=" + dividend +
                ", result=" + result +
                ", firstMove=" + firstMove +
                '}';
    }

    private Optional<GameMove> attemptMove(int addend) {
        int dividend = result + addend;
        if (isDivisibleByThree(dividend)) {
            return Optional.of(new GameMove(this.result, addend, dividend, dividend / 3));
        }

        return Optional.empty();
    }

    private boolean isDivisibleByThree(int number) {
        return number % 3 == 0;
    }

}
