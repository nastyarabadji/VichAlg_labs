package laba3;

import java.util.Random;

class Task { // подобен узлу
    String type; // Тип задачи (T1, T2, T3)
    Task next;   // Ссылка на следующую задачу
    int executionTime; // Время выполнения задачи

    public Task(String type) {
        this.type = type;
        this.executionTime = new Random().nextInt(10) + 1; // Случайное время выполнения от 1 до 10
        this.next = null;
    }
}

class Queue {
    Task front; // Начало очереди
    Task rear;  // Конец очереди

    public Queue() {
        this.front = this.rear = null;
    }

    // Добавление задачи в очередь
    public void enqueue(Task task) {
        if (rear == null) {
            front = rear = task;
        } else {
            rear.next = task;
            rear = task;
        }
    }

    // Извлечение задачи из очереди
    public Task dequeue() {
        if (front == null) {
            return null; // Очередь пуста
        }
        Task task = front;
        front = front.next; // перебрасываем указатель front на следующий элемент
        if (front == null) { // если был 1 элемент
            rear = null;
        }
        return task;
    }

    // Проверка, пуста ли очередь
    public boolean isEmpty() {
        return front == null;
    }
}

class Stack1 {
    Task top; // Вершина стека

    public Stack1() {
        this.top = null;
    }

    // Добавление задачи в стек
    public void push(Task task) {
        task.next = top;
        top = task;
    }

    // Извлечение задачи из стека
    public Task pop() {
        if (top == null) {
            return null; // Стек пуст
        }
        Task task = top;
        top = top.next;
        return task;
    }

    // Проверка, пуст ли стек
    public boolean isEmpty() {
        return top == null;
    }
}

class Processor {
    boolean isFree; // Состояние процессора
    int remainingTime; // Оставшееся время выполнения задачи

    public Processor() {
        this.isFree = true;
        this.remainingTime = 0;
    }

    // Запуск задачи
    public void startTask(Task task) {
        this.isFree = false;
        this.remainingTime = task.executionTime;
    }

    // Обработка времени выполнения
    public void process() {
        if (!isFree) {
            remainingTime--;
            if (remainingTime <= 0) {
                isFree = true; // Освобождаем процессор
            }
        }
    }
}

class TaskScheduler {
    private Queue queue; // Очередь задач
    private Stack1 stack; // Стек задач
    private Processor[] processors; // Массив процессоров

    public TaskScheduler() {
        this.queue = new Queue();
        this.stack = new Stack1();
        this.processors = new Processor[3];
        for (int i = 0; i < 3; i++) {
            processors[i] = new Processor();
        }
    }

    // Добавление задачи в систему
    public void addTask(String type) {
        Task task = new Task(type);
        queue.enqueue(task);
    }

    // Моделирование работы системы
    public void simulate() {
        while (!queue.isEmpty() || !stack.isEmpty()) {
            // Обработка очереди
            if (!queue.isEmpty()) {
                Task task = queue.front;
                int processorIndex = getProcessorIndex(task.type); // получаем индекс процессора по типу задачи

                if (processorIndex != -1 && processors[processorIndex].isFree) {
                    // Процессор свободен, отправляем задачу на выполнение
                    System.out.println("Задача " + task.type + " отправлена на процессор P" + (processorIndex + 1));
                    processors[processorIndex].startTask(queue.dequeue()); // Запускаем задачу
                } else {
                    // Процессор занят, отправляем задачу в стек
                    stack.push(queue.dequeue());
                    System.out.println("Задача " + task.type + " отправлена в стек");
                }
            }

            // Обработка стека
            if (!stack.isEmpty()) {
                Task task = stack.top;
                int processorIndex = getProcessorIndex(task.type);

                if (processorIndex != -1 && processors[processorIndex].isFree) {
                    // Процессор свободен, отправляем задачу на выполнение
                    System.out.println("Задача " + task.type + " извлечена из стека и отправлена на процессор P" + (processorIndex + 1));
                    processors[processorIndex].startTask(stack.pop()); // Запускаем задачу
                }
            }

            // Освобождение процессоров (имитация завершения задач)
            for (int i = 0; i < processors.length; i++) {
                processors[i].process();
                if (processors[i].isFree) {
                    System.out.println("Процессор P" + (i + 1) + " освободился");
                }
            }
        }
    }

    // Получение индекса процессора по типу задачи
    private int getProcessorIndex(String type) {
        switch (type) {
            case "T1": return 0;
            case "T2": return 1;
            case "T3": return 2;
            default: return -1;
        }
    }
}

public class task3 {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        // Добавляем задачи в систему
        scheduler.addTask("T1");
        scheduler.addTask("T2");
        scheduler.addTask("T3");
        scheduler.addTask("T1");
        scheduler.addTask("T2");

        // Запуск моделирования
        scheduler.simulate();
    }
}