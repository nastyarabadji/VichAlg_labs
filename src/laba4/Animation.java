package laba4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Animation extends Application {
    private double[][] matrix;

    static ArrayList<ArrayList<Double>> bucketSort(ArrayList<Double> arr, boolean ascending) {
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
        } else {
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

        return buckets; // Возвращаем массивы карманов
    }

    static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Анимация сортировки");

        // Инициализация массива
        matrix = new double[10][10];
        Random random = new Random();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double value = 60 + (140 - 60) * random.nextDouble();
                matrix[i][j] = roundToTwoDecimalPlaces(value);
            }
        }

        // Создание таблицы для отображения массива
        GridPane gridPane = new GridPane();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Label cellLabel = new Label(String.valueOf(matrix[i][j]));
                cellLabel.setStyle("-fx-border-color: black; -fx-padding: 5px;");

                // Увеличиваем размеры ячеек
                cellLabel.setPrefSize(80, 50); // Увеличенные размеры

                gridPane.add(cellLabel, j, i);
            }
        }

        Button showBucketsButton = new Button("Массивы карманов");

        showBucketsButton.setOnAction(e -> showBucketsArray());

        Button showSortedButton = new Button("Отсортированный массив");

        showSortedButton.setOnAction(e -> showSortedArray());

        HBox buttonBox = new HBox(10, showBucketsButton, showSortedButton); // Расположение кнопок горизонтально

        VBox vbox = new VBox(gridPane, buttonBox);

        Scene scene = new Scene(vbox, 600, 400);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void showBucketsArray() {
        Stage bucketsStage = new Stage();
        bucketsStage.setTitle("Массивы карманов");

        GridPane bucketsGridPane = new GridPane();

        int colorIndex = 0;
        String[] colors = {"#FFB3BA", "#FFDFBA", "#FFFFBA", "#BAFFC9", "#BAE1FF", "#FFBAF3", "#FFD1BA", "#FFE5BA", "#D1BAFF"};

        for (int i = 0; i < matrix.length; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (double element : matrix[i]) {
                row.add(element);
            }

            boolean ascending = (i % 2 == 0); // Нечетные строки сортируем по возрастанию
            ArrayList<ArrayList<Double>> bucketsRow = bucketSort(row, ascending);

            // Отображаем массивы карманов в виде ячеек с цветами
            int columnIndex = 0;
            for (ArrayList<Double> bucket : bucketsRow) {
                if (!bucket.isEmpty()) { // Если карман не пустой
                    String colorForBucket = colors[colorIndex % colors.length];
                    for (Double value : bucket) {
                        Label labelBucketValue = new Label(String.valueOf(value));
                        labelBucketValue.setStyle("-fx-background-color: " + colorForBucket + "; -fx-border-color: black; -fx-padding: 5px;");
                        labelBucketValue.setPrefSize(80, 50); // Увеличенные размеры

                        bucketsGridPane.add(labelBucketValue, columnIndex++, i); // Добавляем в строку карманов
                    }
                    colorIndex++;
                }
            }
        }

        Scene bucketsScene = new Scene(bucketsGridPane, 600, 400);
        bucketsStage.setScene(bucketsScene);
        bucketsStage.show();
    }

    private void showSortedArray() {
        Stage sortedStage = new Stage();
        sortedStage.setTitle("Отсортированный массив");

        GridPane sortedGridPane = new GridPane();

        for (int i = 0; i < matrix.length; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (double element : matrix[i]) {
                row.add(element);
            }

            boolean ascendingOrderForRow;

            if(i % 2 != 0){
                ascendingOrderForRow=false; // Четные строки сортируем по убыванию
            } else{
                ascendingOrderForRow=true; // Нечетные строки сортируем по возрастанию
            }

            Collections.sort(row, ascendingOrderForRow ? Comparator.naturalOrder() : Comparator.reverseOrder());

            // Обновляем матрицу с отсортированными значениями
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = row.get(j);

                Label cellLabelSorted = new Label(String.valueOf(matrix[i][j]));
                cellLabelSorted.setStyle("-fx-border-color: black; -fx-padding: 5px;");
                cellLabelSorted.setPrefSize(80, 50); // Увеличенные размеры

                sortedGridPane.add(cellLabelSorted, j, i);
            }
        }

        Scene sortedScene = new Scene(sortedGridPane, 600, 400);
        sortedStage.setScene(sortedScene);
        sortedStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}