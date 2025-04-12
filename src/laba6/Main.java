package laba6;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class HashTable {
    private LinkedList<LinkedList<String>> table = new LinkedList<>();
    private int size;
    public HashTable(int size) {
        this.size = size;
        for (int i = 0; i < size; ++i) {
            this.table.add(new LinkedList<>());
        }
    }
    private int hash(String word) {
        return Math.abs(word.hashCode() % size);
    }
    public void insert(String word) {
        int index = hash(word);
        this.table.get(index).add(word);
    }
    public boolean search(String word) {
        int index = hash(word);
        LinkedList<String> bucket = this.table.get(index);
        int comparisons = 0; // счётчик сравнений
        for (String w : bucket) {
            comparisons++; // увеличиваем счётчик
            if (w.equals(word)) {
                System.out.println("Слово найдено. Количество сравнений: " + comparisons);
                return true;
            }
        }
        System.out.println("Слово не найдено. Количество сравнений: " + comparisons);
        return false;
    }

    public void display() {
        for (int i = 0; i < this.size; ++i) {
            System.out.println(i + ": " + this.table.get(i));
        }
    }
    public void delete(char firstLetter) {
        for (int i = 0; i < this.size; ++i) {
            this.table.get(i).removeIf(word -> word.charAt(0) == firstLetter);
        }
    }
}

public class Main {
    public static HashTable readFile(int size) {
        HashTable hashTable = new HashTable(size);
        try {
            FileReader fileReader = new FileReader("src/laba6/text.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strWords = line.split(" ");
                for(String word : strWords) {
                    if (!word.isEmpty()) {
                        hashTable.insert(word);
                    }

                }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hashTable;
    }
    public static String menu() {
        return "1. Вывести таблицу на экран\n2. Поиск слова\n3. Удалить все слова, начинающиеся на определённую букву\n0. Выход";
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int size = 0;
        System.out.print("Введите размерность хеш-таблицы: ");
        size = in.nextInt();
        while (size <= 0) {
            System.out.print("Введите размерность хеш-таблицы: ");
            size = in.nextInt();
        }
        HashTable hashTable = readFile(size);
        while (true) {
            System.out.println(menu());
            System.out.print("Введите номер пункта: ");
            int select = in.nextInt();
            switch (select) {
                case 1:
                    hashTable.display();
                    break;
                case 2:
                    String word = in.next();
                    if (hashTable.search(word)) {
                        System.out.println("Найдено слово " + word);
                    }
                    else {
                        System.out.println("Слово не найдено");
                    }
                    break;
                case 3:
                    System.out.print("Введите символ: ");
                    char letter = in.next().charAt(0);
                    hashTable.delete(letter);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Введите корректное число!");
            }
        }
    }
}
