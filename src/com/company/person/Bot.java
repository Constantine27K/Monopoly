package com.company.person;


public class Bot extends Player {

    public Bot(double money) {
        super(money);
    }

    @Override
    public String getName() {
        return "Bot";
    }

    @Override
    public String toString() {
        return getName();
    }
}
