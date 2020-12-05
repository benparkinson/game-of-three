package com.parkinson.ben.gameofthree.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

public class GameMove {

    @Min(-1)
    @Max(1)
    private final int addend;

    private final int result;

    public GameMove(int result) {
        this(result, 0);
    }

    public GameMove(int result, int addend) {
        this.result = result;
        this.addend = addend;
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

    @Override
    public String toString() {
        return "GameMoveResult{" +
                "result=" + result +
                ", addend=" + addend +
                '}';
    }

    private Optional<GameMove> attemptMove(int addend) {
        int newValue = result + addend;
        if (isDivisibleByThree(newValue)) {
            return Optional.of(new GameMove(newValue / 3, addend));
        }

        return Optional.empty();
    }

    private boolean isDivisibleByThree(int number) {
        return number % 3 == 0;
    }

}
