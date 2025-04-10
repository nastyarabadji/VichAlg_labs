package laba3;

class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    // Добавление элемента в конец списка
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) { // если список пуст
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Вывод списка
    public void printList() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Реверсирование списка
//    public void reverse() {
//        Node<T> current = tail; // Начинаем с конца
//        Node<T> temp;
//
//        while (current != null) {
//            temp = current.prev; // Сохраняем предыдущий узел
//            current.prev = current.next; // Меняем местами prev и next
//            current.next = temp;
//            current = current.next; // Переходим к предыдущему узлу
//        }
//
//        // Меняем head и tail местами
//        temp = head;
//        head = tail;
//        tail = temp;
//    }
    public DoublyLinkedList<T> copyReverse() {
        DoublyLinkedList<T> reversedList = new DoublyLinkedList<>();
        Node<T> current = tail;
        while (current != null) {
            reversedList.add(current.data);
            current = current.prev;
        }
        return reversedList;
    }
}


public class task1 {
    public static void main(String[] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.print("Изначальный список: ");
        list.printList();

        DoublyLinkedList<Integer> reversedList = list.copyReverse();
        System.out.print("Реверсированный список: ");
        reversedList.printList();
    }
}
