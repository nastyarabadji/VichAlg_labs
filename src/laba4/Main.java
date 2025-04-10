package laba4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Main {
    static ArrayList<Double> bucketSort(ArrayList<Double> arr, boolean ascending, int rowIndex) {
        ArrayList<Double> sortedArr = new ArrayList<>();
        ArrayList<ArrayList<Double>> buckets = new ArrayList<>();

        double minElement = Collections.min(arr);
        double maxElement = Collections.max(arr);

        double leftBorder = Math.floor(minElement / 10) * 10;
        if ((int) minElement == minElement) {
            leftBorder -= 10;
        }
        double rightBorder = Math.ceil(maxElement / 10) * 10;

        if (ascending) {
            for (double i = leftBorder; i <= rightBorder; i += 10) { // (]
                ArrayList<Double> bucket = new ArrayList<>();
                for (double element : arr) {
                    if (element > i && element <= (i + 10)) {
                        bucket.add(element);
                    }
                }
                buckets.add(bucket);
            }
        }

        else {
            for (double i = rightBorder; i >= leftBorder; i -= 10) { // (]
                ArrayList<Double> bucket = new ArrayList<>();
                for (double element : arr) {
                    if (element <= i && element > (i - 10)) {
                        bucket.add(element);
                    }
                }
                buckets.add(bucket);
            }
        }

        System.out.println("Сформированные карманы для строки " + (rowIndex + 1) + ": " + buckets);

        // Сортируем и объединяем карманы
        for (ArrayList<Double> bucket : buckets) {
            if (!bucket.isEmpty()) {
                if (ascending) {
                    Collections.sort(bucket); // По возрастанию
                } else {
                    Collections.sort(bucket, Collections.reverseOrder()); // По убыванию
                }
                sortedArr.addAll(bucket);
                System.out.println("Отсортированный карман: " + bucket);
            }
        }

        return sortedArr;
    }

    // Метод для округления числа до двух знаков после запятой
    static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        // Начало измерения времени
        long startTime = System.nanoTime();

        // Основной код программы
        double[][] matrix = new double[10][10];
        Random random = new Random();

        // Заполнение массива случайными числами
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double value = 60 + (140 - 60) * random.nextDouble();
                matrix[i][j] = roundToTwoDecimalPlaces(value);
            }
        }

        // Вывод исходного массива
        System.out.println("\n========== Исходный массив ==========");
        for (double[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("====================================\n");

        // Сортировка строк
        for (int i = 0; i < matrix.length; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (double element : matrix[i]) {
                row.add(element);
            }

            boolean ascending = (i % 2 == 0);
            ArrayList<Double> sortedRow = bucketSort(row, ascending, i);

            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = sortedRow.get(j);
            }
        }

        // Вывод отсортированного массива
        System.out.println("\n========== Отсортированный массив ==========");
        for (double[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("==========================================\n");

        // Конец измерения времени
        long endTime = System.nanoTime();
        long duration = (endTime - startTime); // Время в наносекундах

        System.out.println("Время выполнения алгоритма: " + duration + " наносекунд");
    }
}
