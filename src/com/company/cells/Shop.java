package com.company.cells;

import com.company.Game;
import com.company.person.Bot;
import com.company.person.Person;
import com.company.person.Player;

import java.util.Random;

public class Shop extends Cell {
    private boolean hasOwner = false;
    private double price;
    private double compensation;
    private Player owner;
    private int grade = 1;      // добавил уровень прокачки
    public static double improvementCoeff;
    private static double compensationCoeff;
    private static Random random = new Random();

    public int getGrade() {
        return grade;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        hasOwner = true;
    }

    public boolean HasOwner() {
        return hasOwner;
    }

    public double getPrice() {
        return price;
    }

    private void setPrice() {
        price = random.nextInt(450) + 50;
    }

    private void setCompensation() {
        compensation = 0.5 * price + 0.4 * price * random.nextDouble();
    }

    public Player getOwner() {
        return owner;
    }

    /**
     * Улучшает магазин. Увеличивает стоимость, компенсацию и уровень прокачки
     */
    public void improve() {
        price += improvementCoeff * price;
        compensation += compensationCoeff * compensation;
        grade++;
    }

    public double getImprovePrice() {
        return price /** improvementCoeff*/; // спасибо за постоянное изменение условия в слаке
    }

    public double getCompens() {
        return compensation;
    }

    private void setImprovementCoeff() {
        improvementCoeff = 0.1 + 1.9 * random.nextDouble();
    }

    public static double getImprovementCoeff() {
        return improvementCoeff;
    }

    private void setCompensationCoeff() {
        compensationCoeff = 0.1 + 0.9 * random.nextDouble();
    }

    public static double getCompensationCoeff() {
        return compensationCoeff;
    }

    public Shop() {
        setPrice();
        setCompensation();
        setCompensationCoeff();
        setImprovementCoeff();
    }

    /**
     * Вывод в зависимости от того, для кого отображается поле
     *
     * @return символ магазина
     */
    @Override
    public String toString() {
        if (owner instanceof Bot && Game.PoV instanceof Bot ||
                owner instanceof Person && Game.PoV instanceof Person)
            return "M";
        if (owner instanceof Bot && Game.PoV instanceof Person ||
                owner instanceof Person && Game.PoV instanceof Bot)
            return "O";
        return "S";
    }
}
