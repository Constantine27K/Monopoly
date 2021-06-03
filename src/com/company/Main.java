package com.company;

import com.company.field.Field;
import com.company.field.Report;
import com.company.person.Bot;
import com.company.person.Person;

/**
 * @author <a href="mailto:klibragimov@edu.hse.ru">Konstantin Ibragimov</a>
 */

public class Main {
    private static Report report = new Report();
    private static int height;
    private static int width;
    private static double money;

    public static void main(String[] args) {
        try {
            checkArr(args);
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            return;
        }
        mainMenu();
    }

    private static void mainMenu() {
        System.out.println("Welcome to Monopoly!\n");
        System.out.println("Here you will play with bot\n");
        System.out.println("You may buy shops, improve them, take a cab, take debts and so on\n");
        System.out.println("So, let's get started!\n");
        gameStart();
    }

    /**
     * Создание игрока и бота, генерация поля, вывод коэффициентов и запусе игры
     */
    private static void gameStart() {
        try {
            Person person = new Person(money);
            Bot bot = new Bot(money);
            Field field = new Field(height, width);
            report.coefInfo();
            report.gamers(person, bot);
            Game game = new Game(person, bot);
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
    }

    /**
     * Проверка правильности введенных аргументов в командную строку
     *
     * @param str аргумент командной строки
     * @return результат Integer.parseInt
     */
    private static int check(String str) {
        int res;
        try {
            res = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            System.out.println("Sorry, you entered wrong arguments");
            throw new NumberFormatException();
        }
        return res;
    }

    /**
     * Проверка количества введенных аргументов и их правильности
     *
     * @param arr аргументы командной строки
     */
    private static void checkArr(String[] arr) {
        if (arr.length != 3)
            throw new IllegalArgumentException("You entered less or more arguments");
        width = check(arr[0]);
        height = check(arr[1]);
        money = check(arr[2]);
    }
}
