package com.company.person;

import com.company.field.Field;

import java.util.Random;

public class Player {
    private int position;
    private double money;
    private double maxMoney;
    private double moneySpentShop;
    private boolean isDebtor = false;
    private String name = "Player";
    private double debt;
    Random random = new Random();

    public double getMoney() {
        return money;
    }

    public void spend(double sum) {
        money -= sum;
    }

    public void spendShop(double sum) {
        moneySpentShop -= sum;
        money -= sum;
    }

    public void earn(double sum) {
        money += sum;
    }

    public boolean isDebtor() {
        return isDebtor;
    }

    public double getMoneySpentShop() {
        return maxMoney - moneySpentShop;
    }

    public String getName() {
        return name;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
        isDebtor = true;
    }

    public void setNotDebt() {
        isDebtor = false;
    }

    public int getPosition() {
        return position;
    }

    public void setMaxMoney(double maxMoney) {
        if (maxMoney < 500 || maxMoney > 15_000) {
            throw new IllegalArgumentException("You entered number out of money bounds");
        }
        this.maxMoney = maxMoney;
    }

    /**
     * Метод для бросания кости и передвижения игрока
     */
    public void throwDice() {
        int step1 = random.nextInt(6) + 1;
        int step2 = random.nextInt(6) + 1;
        int sum = step1 + step2;
        System.out.println(getName() + " moved for " + sum + " cells");
        position = (position + sum) % (Field.getWidth() * 2 + 2 * Field.getHeight() - 4);
    }

    /**
     * Метод для передвижения при попадании на клетку такси
     *
     * @param distance дистанция передвижения
     */
    public void cab(int distance) {
        position += distance;
    }

    public Player(double money) {
        setMaxMoney(money);
        this.money = maxMoney;
        this.moneySpentShop = maxMoney;
    }

    public Player() {
        maxMoney = random.nextInt(14_500 + 1) + 500;
        this.money = maxMoney;
        this.moneySpentShop = maxMoney;
    } // изначально предназначался для бота,
    // чтобы у него имелась рандомная сумма денег

    @Override
    public String toString() {
        return name;
    }
}
