package laba2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void swapChetNechet(ArrayList<Integer> arr) {
        for (int i = 0; i < arr.size(); ++i) {
            if (arr.get(i) % 2 == 0) {
                for (int j = i + 1; j < arr.size(); ++j) {
                    if (arr.get(j) % 2 != 0) {
                        Collections.swap(arr, i, j);
                    }
                }
            }
        }
    }
    public static int area(int[][] matrix) {
        int maxArea = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1) { // Начинаем только с единиц
                    for (int k = i; k < rows; k++) { // Проходим вниз
                        if (matrix[k][j] == 0) { // Если встречаем 0, выходим
                            break;
                        }
                        for (int l = j; l < cols; l++) { // Проходим вправо
                            if (matrix[k][l] == 0) { // Если встречаем 0, выходим
                                break;
                            }
                            // Проверяем, что все элементы в прямоугольнике равны 1
                            boolean isValid = true;
                            for (int x = i; x <= k; x++) {
                                for (int y = j; y <= l; y++) {
                                    if (matrix[x][y] == 0) {
                                        isValid = false;
                                        break;
                                    }
                                }
                                if (!isValid) {
                                    break;
                                }
                            }
                            // Если прямоугольник без нулей, вычисляем площадь
                            if (isValid) {
                                int area = (k - i + 1) * (l - j + 1);
                                maxArea = Math.max(maxArea, area);
                            }
                        }
                    }
                }
            }
        }
        return maxArea; // Возвращаем максимальную площадь
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Введите размер массива: ");
        int n = in.nextInt();
        ArrayList<Integer> arr = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; ++i) {
            arr.add(random.nextInt(30));
        }

        System.out.print("Исходный массив: ");
        for (Integer element: arr) {
            System.out.print(element + " ");
        }
        System.out.print("\n");

        swapChetNechet(arr);

        System.out.print("Изменённый массив: ");
        for (Integer element: arr) {
            System.out.print(element + " ");
        }
        System.out.print("\n");

        System.out.print("Введите количество строк: ");
        n = in.nextInt();
        System.out.print("Введите количество столбцов: ");
        int m = in.nextInt();
        int[][] array = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                array[i][j] = in.nextInt();
            }
            System.out.print("\n");
        }
        int result = area(array);
        System.out.print("Максимальная площадь: " + result);
    }
}
