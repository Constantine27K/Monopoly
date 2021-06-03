package com.company;

import com.company.cells.*;
import com.company.field.Field;
import com.company.field.Report;
import com.company.person.Bot;
import com.company.person.Person;
import com.company.person.Player;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player gamer;
    private Player bot;
    private static Random random = new Random();
    private final int move = random.nextInt(2);
    private Report report = new Report();
    private Scanner scanner = new Scanner(System.in);
    public static boolean gameGoes = true;
    private String answer;
    private double digitAnswer;
    public static Player PoV;

    public Game(Person person, Bot bot) {
        this.gamer = person;
        this.bot = bot;
        whoMoves();
    }

    /**
     * Метод, определяющий кто ходит первый
     */
    private void whoMoves() {
        if (move == 0) {
            System.out.println("----- " + gamer.getName() + " moves first -----\n");
            start(gamer, bot);
        } else {
            System.out.println("----- " + bot.getName() + " moves first -----\n");
            start(bot, gamer);
        }
    }

    /**
     * Старт игры и начало каждого раунда
     *
     * @param first  первый игрок
     * @param second второй игрок
     */
    private void start(Player first, Player second) {
        while (gameGoes) {
            System.out.println("--------------------");
            PoV = first;
            first.throwDice();
            checkCell(first.getPosition(), first);
            if (!gameGoes) break;
            Field.printField();
            PoV = second;
            System.out.println();
            second.throwDice();
            checkCell(second.getPosition(), second);
            if (!gameGoes) break;
            Field.printField();
            report.gamers(first, second);
        }
    }

    /**
     * Метод для проверки на какой клетке стоит игрок
     *
     * @param position позиция игрока
     * @param gamer    игрок
     */
    private void checkCell(int position, Player gamer) {
        int[] coord = Field.coordinates(position);
        Cell cell = Field.getCell(coord);
        if (cell instanceof Shop)
            isShop(gamer, ((Shop) cell));
        if (cell instanceof Bank)
            isBank(gamer, ((Bank) cell));
        if (cell instanceof Taxi)
            isTaxi(gamer, ((Taxi) cell));
        if (cell instanceof PenaltyCell)
            isPenalty(gamer, ((PenaltyCell) cell));
        if (cell instanceof EmptyCell)
            isEmpty(((EmptyCell) cell));
    }

    /**
     * Если игрок стоит на клетке магазина,
     * выводится информация о клетке и начинается диалог с игроком
     *
     * @param gamer игрок
     * @param cell  клетка
     */
    private void isShop(Player gamer, Shop cell) {
        report.shop(cell);
        shopDialog(gamer, cell);
    }

    /**
     * Если игрок стоит на клетке банка,
     * выводится информация о клетке и начинается диалог с игроком
     *
     * @param gamer игрок
     * @param cell  клетка
     */
    private void isBank(Player gamer, Bank cell) {
        report.bank(cell);
        bankDialog(gamer);
    }

    /**
     * Если игрок стоит на клетке такси,
     * выводится информация о клетке и начинается диалог с игроком
     *
     * @param gamer игрок
     * @param cell  клетка
     */
    private void isTaxi(Player gamer, Taxi cell) {
        report.taxi(cell);
        taxiDialog(gamer, cell);
    }

    /**
     * Если игрок стоит на штрафной клетке,
     * выводится информация о клетке и начинается диалог с игроком
     *
     * @param gamer игрок
     * @param cell  клетка
     */
    private void isPenalty(Player gamer, PenaltyCell cell) {
        report.penaltyCell(cell);
        penaltyDialog(gamer);
    }

    /**
     * Если игрок стоит на пустой клетке,
     * выводится информация о клетке и начинается диалог с игроком
     *
     * @param cell
     */
    private void isEmpty(EmptyCell cell) {
        report.emptyCell(cell);
    }

    /**
     * Диалог с игроком при попадании на клетку магазина
     *
     * @param gamer игрок
     * @param shop  магазин
     */
    private void shopDialog(Player gamer, Shop shop) {
        if (!shop.HasOwner())
            ifHasNoOwner(gamer, shop);
        else if (shop.getOwner() == gamer)
            ifOwner(gamer, shop);
        else if (shop.getOwner() != gamer)
            ifNotOwner(gamer, shop);
    }

    /**
     * Проверка не является ли игрок банкротом
     *
     * @param gamer игрок
     */
    private void checkMoneyStatusFail(Player gamer) {
        if (gamer.getMoney() <= 0) {
            System.out.println("\n" + gamer.getName() + " lose this game!");
            gameGoes = false;
            scanner.close();
            //start(gamer, gamer);
        }
    }

    /**
     * Проверка на то, может ли игрок купить магазин
     *
     * @param gamer игрок
     * @param shop  магазин
     * @return результат проверки
     */
    private boolean checkCanBuy(Player gamer, Shop shop) {
        return gamer.getMoney() - shop.getPrice() > 0;
    }

    /**
     * Проверка на то, може =т ли игрок улучшить магазин
     *
     * @param gamer игрок
     * @param shop  магазин
     * @return результат проверки
     */
    private boolean checkCanImprove(Player gamer, Shop shop) {
        return gamer.getMoney() - Shop.getImprovementCoeff() * shop.getPrice() > 0;
    }

    /**
     * Проверка ввода игрока
     *
     * @return ответ игрока
     */
    private String checkInput() {
        answer = scanner.next();
        while (!answer.equalsIgnoreCase("yes") &&
                !answer.equalsIgnoreCase("no")) {
            System.out.println("Please enter 'yes' or 'no'");
            answer = scanner.next();
        }
        return answer;
    }

    /**
     * Диалог при условии, что игрок владелец магазина
     *
     * @param gamer игрок
     * @param shop  магазин
     */
    private void ifOwner(Player gamer, Shop shop) {
        System.out.println("Would you like to improve your shop for " + Math.round(shop.getPrice()) + "$ ?");
        ifBot(gamer);
        switch (answer) {
            case "yes":
                if (checkCanImprove(gamer, shop)) {
                    gamer.spendShop(Math.round(shop.getImprovePrice()));
                    shop.improve();
                    System.out.println("You improved the shop! It's grade: " + shop.getGrade());
                } else {
                    System.out.println("Sorry you do not have required amount");
                }
                break;
            case "no":
                System.out.println("You did not improve the shop");
                break;
        }
    }

    /**
     * Диалог при условии, что магазин не имеет владельца
     *
     * @param gamer игрок
     * @param shop  магазин
     */
    private void ifHasNoOwner(Player gamer, Shop shop) {
        System.out.println("Wow! This shop still does not have an owner. " +
                "Would you like to buy it for " + Math.round(shop.getPrice()) + "?");
        ifBot(gamer);
        switch (answer) {
            case "yes":
                if (checkCanBuy(gamer, shop)) {
                    gamer.spendShop(shop.getPrice());
                    shop.setOwner(gamer);
                    System.out.println("Congratulations! You bought the shop!");
                } else {
                    System.out.println("Oh... You can not buy this shop. Your finances sing romances");
                }
                break;
            case "no":
                System.out.println("You did not buy the shop");
                break;
        }
    }

    /**
     * Диалог при условии, что игрок не владелец магазина
     *
     * @param gamer игрок
     * @param shop  магазин
     */
    private void ifNotOwner(Player gamer, Shop shop) {
        System.out.println("It seems you get in your opponent's shop...\n" +
                "So you need to pay compensation " + Math.round(shop.getCompens()) + "$");
        gamer.spend(Math.round(shop.getCompens()));
        shop.getOwner().earn(Math.round(shop.getCompens()));
        checkMoneyStatusFail(gamer);
    }

    /**
     * Диалог с игроком при попадании на клетку банка
     *
     * @param gamer игрок
     */
    private void bankDialog(Player gamer) {
        if (gamer instanceof Bot) {
            System.out.println("Bots can not use banks!");
            return;
        }
        if (gamer.isDebtor()) {
            System.out.println("Oh! You are the debtor! So you need to pay "
                    + Math.round(gamer.getDebt()));
            gamer.spend(Math.round(gamer.getDebt()));
            gamer.setNotDebt();
            checkMoneyStatusFail(gamer);
        } else {
            if (gamer.getMoneySpentShop() == 0) {
                System.out.println("You can not take a credit because you did not buy or improve any shop");
                return;
            }
            System.out.println("Would you like to get a credit?");
            ifBot(gamer);
            takeDebt();
        }
    }

    /**
     * Диалог с игроком, если он не является должником банка
     */
    private void takeDebt() {
        switch (answer) {
            case "yes":
                System.out.println("Please input how much you want\n" +
                        "You can take a debt between 0 and "
                        + Math.round(Bank.getCreditCoeff() * gamer.getMoneySpentShop()));
                digitAnswer = checkDebt(Bank.getCreditCoeff() * gamer.getMoneySpentShop());
                gamer.earn(digitAnswer);
                gamer.setDebt(Bank.getDebtCoeff() * digitAnswer);
                System.out.println("Fine! Your credit is confirmed!");
                break;
            case "no":
                System.out.println("Ok! May be next time");
                break;
        }
    }

    /**
     * Метод принимающий от игрока сумму кредита
     *
     * @param bound
     * @return
     */
    private double checkDebt(double bound) {
        digitAnswer = scanner.nextDouble();
        while (digitAnswer <= 0 || digitAnswer > Math.round(bound)) {
            System.out.println("Please enter a number between 0 and " + Math.round(bound));
            digitAnswer = scanner.nextDouble();
        }
        return Math.round(digitAnswer);
    }

    /**
     * Диалог с игроком при попадании на клетку такси
     *
     * @param gamer игрок
     * @param taxi  такси
     */
    private void taxiDialog(Player gamer, Taxi taxi) {
        int distance = taxi.getDistance();
        gamer.cab(distance);
        System.out.println("You are shifted forward for " + distance + " cells");
    }

    /**
     * Диалог с игроком при попадании на штрафную клетку
     *
     * @param gamer
     */
    private void penaltyDialog(Player gamer) {
        System.out.println("I think you should pay now "
                + Math.round(PenaltyCell.getPenaltyCoeff() * gamer.getMoney()) + "$");
        gamer.spend(Math.round(PenaltyCell.getPenaltyCoeff() * gamer.getMoney()));
        checkMoneyStatusFail(gamer);
    }

    /**
     * Проверка на то, является ли игрок ботом.
     * В зависимости от этого либо генерируется ответ,
     * либо ответ просят ввести в консоль
     *
     * @param gamer игрок
     */
    private void ifBot(Player gamer) {
        if (gamer instanceof Bot) {
            int ans = random.nextInt(2);
            if (ans == 0)
                answer = "yes";
            else
                answer = "no";
            System.out.println(answer);
        } else {
            answer = checkInput();
        }
    }
}
