package com.company.cells;

import java.util.Random;

public class Bank extends Cell {
    private static Random random = new Random();
    private static final double creditCoeff = 0.002 + 0.198 * random.nextDouble();

    private static final double debtCoeff = 1.0 + 2.0 * random.nextDouble();

    public static double getCreditCoeff() {
        return creditCoeff;
    }

    public static double getDebtCoeff() {
        return debtCoeff;
    }

    @Override
    public String toString() {
        return "$";
    }
}
