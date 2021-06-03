package com.company.field;

import com.company.Game;
import com.company.cells.*;
import com.company.person.Player;

public class Report {

    /**
     * Вывод информации при попадании на клетку магазина
     *
     * @param shop клетка магазина
     */
    public void shop(Shop shop) {
        String context = "";
        context += "You are in SHOP at " + coordinatesCell(shop) + ". ";
        if (shop.HasOwner())
            context += shop.getOwner() + " owns this place";
        System.out.println(context);
    }

    /**
     * Вывод информации при попадании на клетку банка
     *
     * @param bank клетка банка
     */
    public void bank(Bank bank) {
        System.out.println("You are in BANK OFFICE at " + coordinatesCell(bank));
    }

    /**
     * Вывод информации при попадании на пустую клетку
     *
     * @param emptyCell пустая клетка
     */
    public void emptyCell(EmptyCell emptyCell) {
        System.out.println("You are in EMPTY CELL at " + coordinatesCell(emptyCell));
        System.out.println("Just relax");
    }

    /**
     * Вывод информации при попадании на штрафную клетку
     *
     * @param penaltyCell штрафная клетка
     */
    public void penaltyCell(PenaltyCell penaltyCell) {
        System.out.println("You are in PENALTY POINT at " + coordinatesCell(penaltyCell));
    }

    /**
     * Вывод информации при попадании на клетку такси
     *
     * @param taxi клетка такси
     */
    public void taxi(Taxi taxi) {
        System.out.println("You got into a TAXI at " + coordinatesCell(taxi) + ". Check your seat belt!");
    }

    /**
     * Вывод информации коэфициэнтов
     */
    public void coefInfo() {
        System.out.println("Debt coeff: " + Math.round(Bank.getDebtCoeff() * 100.0) / 100.0);
        System.out.println("Credit coeff: " + Math.round(Bank.getCreditCoeff() * 100.0) / 100.0);
        System.out.println("Penalty coeff: " + Math.round(PenaltyCell.getPenaltyCoeff() * 100.0) / 100.0);
        System.out.println("Improvement coeff: " + Math.round(Shop.getImprovementCoeff() * 100.0) / 100.0);
        System.out.println("Compensation coeff: " + Math.round(Shop.getCompensationCoeff() * 100.0) / 100.0 + "\n");
    }

    /**
     * Вывод основной информации об игроке
     *
     * @param player игрок
     */
    public void gamer(Player player) {
        String context = "";
        context += player + " is at " + coordinatesPlayer(player) + "\n";
        context += player + " have " + Math.round(player.getMoney()) + "$. ";
        if (player.isDebtor())
            context += player + " is also a bank debtor";
        System.out.println(context);
    }

    /**
     * Вывод информации об обоих игроках
     *
     * @param player1 игрок №1
     * @param player2 игрок №2
     */
    public void gamers(Player player1, Player player2) {
        if (!Game.gameGoes)
            return;
        System.out.println("\n");
        gamer(player1);
        gamer(player2);
        System.out.println("\n");
    }

    /**
     * Метод для нахождения индексов клетки в массиве
     *
     * @param cell клетка
     * @return координаты клетки
     */
    private int[] findIndexes(Cell cell) {
        int[] arr = new int[2];
        for (int i = 0; i < Field.getHeight(); i++) {
            for (int j = 0; j < Field.getWidth(); j++) {
                if (Field.getCell(i, j) == cell) {
                    arr[0] = i;
                    arr[1] = j;
                    return arr;
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * Сбор строки с координатами клетки
     *
     * @param cell клетка
     * @return координаты клетки
     */
    private String coordinatesCell(Cell cell) {
        int[] res = findIndexes(cell);
        //if (res[0] > 0) res[0] = -res[0];
        return "(" + res[1] + ", " + -res[0] + ")";
    }

    /**
     * Сбор строки с координатами игрока
     *
     * @param player игрок
     * @return координаты игрока
     */
    private String coordinatesPlayer(Player player) {
        int[] res = Field.coordinates(player.getPosition());
        return "(" + res[1] + ", " + -res[0] + ")";
    }
}
