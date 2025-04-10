package laba1;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void convert_2_16(int value) {
        String valueDvoich = String.valueOf(value);
        while(!(valueDvoich.length() >= 4 && (valueDvoich.length() & (valueDvoich.length() - 1)) == 0)) { // проверка чётности длины
            valueDvoich = "0" + valueDvoich; // добавление '0' пока длина не станет значением степени двойки
        }
        ArrayList<String> masValue = new ArrayList<>();
        for (int i = 0; i <= valueDvoich.length() - 4; i += 4) {
            masValue.add(valueDvoich.substring(i, i + 4)); // разбиваем строку с двоичным числом по 'четвёркам'
        }
        String result = "";
        String alph_16 = "abcdef";
        for (String i: masValue) { // переводим каждую 'четвёрку' в 16-ричную систему
            int buff = 0;
            for(int j = 0; j < 4; j++) {
                if(i.charAt(j) == '1') {
                    buff += Math.pow(2, 3 - j);
                }
            }
            if(buff > 9 && buff < 16) {
                result += alph_16.charAt(buff - 10);
            }
            else {
                result += String.valueOf(buff);
            }
        }
        System.out.println(result);
    }
    public static void convert_16_2(String value) {
        String alph_16 = "abcdef";
        String result = "";
        for(int i = 0; i < value.length(); ++i) {
            String subResult = "";
            int buffNumber = 0;
            try {
                buffNumber = Integer.parseInt(String.valueOf(value.charAt(i))); // переводим каждую цифру в int
                while(buffNumber > 0) { // переводим каждую в двоичную систему
                    subResult = String.valueOf(buffNumber % 2) + subResult;
                    buffNumber /= 2;
                }
                if(value.charAt(i) == '0') {
                    subResult = "0000";
                }
            }
            catch (NumberFormatException e) { // если попалась буква
                buffNumber = alph_16.indexOf(value.charAt(i)) + 10; // переводим её в число
                while(buffNumber > 0) { // переводим полученное число в двочную систему
                    subResult = String.valueOf(buffNumber % 2) + subResult;
                    buffNumber /= 2;
                }
            }
            while (subResult.length() < 4) { // добавление незначащих нулей до образования 'четвёрки'
                subResult = "0" + subResult;
            }
            result += subResult; // добавление к итоговому результату новой двоичной 'четвёрки'
        }
        System.out.println(result);
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Введите двоичное число: ");
        int value1 = in.nextInt();
        long startTime1 = System.nanoTime(); // Начало отсчета времени
        System.out.print("Шестнадцатеричная запись: ");
        convert_2_16(value1);
        long endTime1 = System.nanoTime(); // Конец отсчета времени
        System.out.printf("Время выполнения: %.9f секунд%n", (endTime1 - startTime1) / 1_000_000_000.0);

        System.out.print("Введите шестнадцатеричное число: ");
        String value2 = in.next();
        long startTime2 = System.nanoTime(); // Начало отсчета времени
        System.out.print("Двоичная запись: ");
        convert_16_2(value2);
        long endTime2 = System.nanoTime(); // Конец отсчета времени
        System.out.printf("Время выполнения: %.9f секунд%n", (endTime2 - startTime2) / 1_000_000_000.0);
    }
}
