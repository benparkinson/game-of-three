package com.parkinson.ben.gameofthree.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ManualGameMove {

    private final int previousResult;
    @Min(-1)
    @Max(1)
    private final int addend;

    public ManualGameMove(int previousResult, int addend) {
        this.previousResult = previousResult;
        this.addend = addend;
    }

    public GameMove convertToGameMove() {
        int dividend = previousResult + addend;
        return new GameMove(previousResult, addend, dividend, dividend / 3);
    }

    public int getPreviousResult() {
        return previousResult;
    }

    public int getAddend() {
        return addend;
    }

    @Override
    public String toString() {
        return "ManualGameMove{" +
                "previousResult=" + previousResult +
                ", addend=" + addend +
                '}';
    }
}
