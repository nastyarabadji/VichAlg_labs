package laba5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class task1 {
    public static void task1() {
        List<Integer> numbers = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        // Считываем последовательность чисел
        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break; // Завершаем ввод при вводе 0
            }
            numbers.add(n); // Добавляем число в список
        }

        // Выводим числа на нечетных позициях
        printOddPositionNumbers(numbers, 1);
    }

    public static void printOddPositionNumbers(List<Integer> numbers, int position) {
        if (position > numbers.size()) {
            return; // Завершение, если достигли конца списка
        }
        if (position % 2 == 1) {
            System.out.println(numbers.get(position - 1)); // Выводим число на нечетной позиции
        }
        printOddPositionNumbers(numbers, position + 1); // Рекурсивный вызов
    }

    public static void main(String[] args) {
        task1();
    }
}
