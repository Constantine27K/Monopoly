package com.company.cells;

import java.util.Random;

public class PenaltyCell extends Cell {
    private static Random random = new Random();
    private static final double penaltyCoeff = 0.01 + 0.99 * random.nextDouble();

    public static double getPenaltyCoeff() {
        return penaltyCoeff;
    }

    @Override
    public String toString() {
        return "%";
    }
}
