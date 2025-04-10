package laba3;

import java.util.Scanner;
import java.util.Stack;

class Node {
    int data;       // Данные узла
    Node next;      // Ссылка на следующий узел

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class Stack2 {
    private Node top; // Вершина стека
    private int size; // Количество элементов в стеке

    // Конструктор
    public Stack2() {
        this.top = null;
        this.size = 0;
    }

    // Добавление элемента в стек
    public void push(int data) {
        Node newNode = new Node(data);
        newNode.next = top; // Новый узел ссылается на текущий top
        top = newNode;      // Обновляем top на новый узел
        size++;             // Увеличиваем размер стека
    }

    // Удаление и возврат верхнего элемента стека
    public int pop() {
        if (top == null) {
            throw new IllegalStateException("Стек пуст");
        }
        int data = top.data; // Сохраняем данные из top
        top = top.next;      // Обновляем top на следующий узел
        size--;             // Уменьшаем размер стека
        return data;
    }

    // Проверка, пуст ли стек
    public boolean isEmpty() {
        return top == null;
    }

    // Получение размера стека
    public int getSize() {
        return size;
    }
}

public class task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack2 customStack = new Stack2();
        Stack<Integer> standardStack = new Stack<>();

        System.out.println("Введите числа (для завершения введите 'exit'):");

        while (true) {
            System.out.print("Введите число: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break; // Завершение ввода
            }

            try {
                int number = Integer.parseInt(input); // Преобразуем ввод в число

                // Измерение времени для пользовательского стека
                long startTime = System.nanoTime();
                processNumber(customStack, number);
                long endTime = System.nanoTime();
                System.out.println("Время выполнения пользовательского стека: " + (endTime - startTime) + " нс");
                System.out.println();

                // Измерение времени для стандартного стека
                startTime = System.nanoTime();
                processStandardStack(standardStack, number);
                endTime = System.nanoTime();
                System.out.println("Время выполнения стандартного стека: " + (endTime - startTime) + " нс");
                System.out.println();

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число или 'exit' для завершения.");
            }
        }

        scanner.close();
    }

    private static void processNumber(Stack2 stack, int number) {
        // Преобразуем число в строку
        String numberStr = Integer.toString(number);

        // Записываем цифры числа в стек в порядке их следования
        for (char c : numberStr.toCharArray()) {
            int digit = Character.getNumericValue(c); // Преобразуем символ в число
            stack.push(digit); // Добавляем цифру в стек
        }

        // Формируем перевёрнутое число
        int reversedNumber = 0;
        int size = stack.getSize(); // Получаем количество цифр в числе

        while (!stack.isEmpty()) {
            int digit = stack.pop(); // Извлекаем цифру из стека
            reversedNumber += digit * (int) Math.pow(10, size - 1); // Добавляем к числу с учётом разряда
            size--; // Уменьшаем разряд
        }

        // Выводим результаты
        System.out.println("Оригинальное число: " + number);
        System.out.println("Перевёрнутое число без использования стандартных коллекций: " + reversedNumber);
    }

    private static void processStandardStack(Stack<Integer> stack, int number) {
        // Преобразуем число в строку
        String numberStr = Integer.toString(number);

        // Записываем цифры числа в стандартный стек в порядке их следования
        for (char c : numberStr.toCharArray()) {
            int digit = Character.getNumericValue(c); // Преобразуем символ в число
            stack.push(digit); // Добавляем цифру в стек
        }

        // Формируем перевёрнутое число
        int reversedNumber = 0;
        int size = stack.size(); // Получаем количество цифр в числе

        while (!stack.isEmpty()) {
            int digit = stack.pop(); // Извлекаем цифру из стека
            reversedNumber += digit * (int) Math.pow(10, size - 1); // Добавляем к числу с учётом разряда
            size--; // Уменьшаем разряд
        }

        // Выводим результаты
        System.out.println("Оригинальное число: " + number);
        System.out.println("Перевёрнутое число с использованием стандартных коллекций: " + reversedNumber);
    }
}