package com.company.cells;

import java.util.Random;

public class Taxi extends Cell {
    private static Random random = new Random();

    public int getDistance() {
        return random.nextInt(3) + 3;
    }

    @Override
    public String toString() {
        return "T";
    }
}
