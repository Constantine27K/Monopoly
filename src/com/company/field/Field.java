package com.company.field;

import com.company.cells.*;

import java.util.Random;

public class Field {
    private static int height;
    private static int width;
    private static Random random = new Random();
    private static Cell[][] cells;
    private Cell[] left;
    private Cell[] right;

    public static int getHeight() {
        return height;
    }

    private void setHeight(int _height) {
        if (_height < 6 || _height > 30)
            throw new IllegalArgumentException("Height is out of bounds");
        height = _height;
    }

    public static int getWidth() {
        return width;
    }

    private void setWidth(int _width) {
        if (_width < 6 || _width > 30)
            throw new IllegalArgumentException("Width is out of bounds");
        width = _width;
    }

    public Field(int height, int width) {
        setHeight(height);
        setWidth(width);
        cells = new Cell[height][width];
        left = new Cell[height];
        right = new Cell[height];
        genField();
        printField();
    }

    /**
     * Вывод поля в консоль
     */
    public static void printField() {
        printHorizontal(getWidth(), 0);
        printVertical(getWidth());
        printHorizontal(getWidth(), height - 1);
    }

    /**
     * Вспомогательный метод для отрисовки вертикальных сторон поля
     *
     * @param width ширина поля
     */
    private static void printVertical(int width) {
        for (int i = 1; i < height - 1; i++) {
            System.out.print("|" + cells[i][0] + "|");
            for (int j = 0; j < width * 3 - 6; j++) {
                System.out.print(" ");
            }
            System.out.println("|" + cells[i][width - 1] + "|");
        }
    }

    /**
     * Вспомогательный метод для отрисовки горизонтальных сторон поля
     *
     * @param width ширина поля
     * @param i     номер строки
     */
    private static void printHorizontal(int width, int i) {
        System.out.print("|E|");
        for (int j = 1; j < width - 1; j++) {
            System.out.print("|" + cells[i][j].toString() + "|");
        }
        System.out.println("|E|");
    }

    /**
     * Генерация клеток поля
     */
    private void genField() {
        genRange(cells[0]);
        genRange(cells[height - 1]);
        genRange(left);
        genRange(right);
        rotateRight(right);
        rotateLeft(left);
    }

    /**
     * Генерация клеток на определенной стороне
     *
     * @param arr массив клеток - сторона поля
     */
    private void genRange(Cell[] arr) {
        arr[0] = new EmptyCell();
        addBank(arr);
        addPenalty(arr);
        addTaxi(arr);
        addShop(arr);
        arr[arr.length - 1] = new EmptyCell();
    }

    /**
     * Добавление банка на сторону
     *
     * @param arr массив клеток - сторона поля
     */
    private void addBank(Cell[] arr) {
        int bankCell = random.nextInt(arr.length - 2) + 1;
        while (arr[bankCell] != null) {
            bankCell = random.nextInt(arr.length - 2) + 1;
        }
        arr[bankCell] = new Bank();
    }

    /**
     * Добавление такси на сторону
     *
     * @param arr массив клеток - сторона поля
     */
    private void addTaxi(Cell[] arr) {
        int taxi = random.nextInt(3);
        for (int i = 0; i < taxi; i++) {
            int taxiCell = random.nextInt(arr.length - 2) + 1;
            while (arr[taxiCell] != null)
                taxiCell = random.nextInt(arr.length - 2) + 1;
            arr[taxiCell] = new Taxi();
        }
    }

    /**
     * Добавление штрафной клетки на сторону
     *
     * @param arr массив клеток - сторона поля
     */
    private void addPenalty(Cell[] arr) {
        int penalty = random.nextInt(3);
        for (int i = 0; i < penalty; i++) {
            int penaltyCell = random.nextInt(arr.length - 2) + 1;
            while (arr[penaltyCell] != null)
                penaltyCell = random.nextInt(arr.length - 2) + 1;
            arr[penaltyCell] = new PenaltyCell();
        }
    }

    /**
     * Добавление магазина на сторону
     *
     * @param arr массив клеток - сторона поля
     */
    private void addShop(Cell[] arr) {
        for (int i = 1; i <= arr.length - 1; i++) {
            if (arr[i] == null)
                arr[i] = new Shop();
        }
    }

    /**
     * Поворот доп. массива для вставки вместо правой стороны основного массива
     *
     * @param arr массив клеток - сторона поля
     */
    private void rotateRight(Cell[] arr) {
        for (int i = width - 1; i < width; i++) {
            for (int j = 0; j < arr.length; j++) {
                cells[j][i] = arr[j];
            }
        }
    }

    /**
     * Поворот доп. массива для вставки вместо левой стороны основного массива
     *
     * @param arr
     */
    private void rotateLeft(Cell[] arr) {
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < arr.length; j++) {
                cells[j][i] = arr[j];
            }
        }
    }

    /**
     * Метод для нахождения координаты клетки
     *
     * @param position позиция игрока на клетке
     * @return массив координат
     */
    public static int[] coordinates(int position) {
        if (position < width)
            return new int[]{0, position};
        if (position > width - 1 && position < width + height - 2) {
            position -= (width - 1);
            return new int[]{position, width - 1};
        }
        if (position >= width + height - 2 && position <= 2 * width + height - 3) {
            position -= (width + height - 2);
            position = (width - 1) - position;
            return new int[]{height - 1, position};
        }
        if (position > 2 * width + height - 3 && position < 2 * (width + height - 2)) {
            position -= 2 * width + height - 4;
            return new int[]{position, 0};
        }
        return new int[]{-1, -1};
    }

    /**
     * Метод возвращает клетку по заданым координатам
     *
     * @param arr координаты
     * @return клетка
     */
    public static Cell getCell(int[] arr) {
        return cells[arr[0]][arr[1]];
    }

    /**
     * Метод возвращает клетку по заданым координатам
     *
     * @param first  Y координата
     * @param second Х координата
     * @return клетка
     */
    public static Cell getCell(int first, int second) {
        return cells[first][second];
    }
}

