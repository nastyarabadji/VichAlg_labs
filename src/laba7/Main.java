package laba7;
import java.util.LinkedList;
import java.util.Queue;

class BinarySearchTree {
    static class Node {
        String value;
        Node left, right, parent;

        Node(String value) {
            this.value = value;
            left = right = parent = null;
        }
    }

    private Node root;

    // 1. Addr(v) - возвращает адрес узла со значением v
    public Node Addr(String v) {
        return findNode(root, v);
    }

    // 2. Value(p) - возвращает значение узла
    public String Value(Node p) {
        return p != null ? p.value : null;
    }

    // 3. Left(p) - возвращает левого потомка
    public Node Left(Node p) {
        return p != null ? p.left : null;
    }

    // 4. Right(p) - возвращает правого потомка
    public Node Right(Node p) {
        return p != null ? p.right : null;
    }

    // 5. Father(p) - возвращает родителя
    public Node Father(Node p) {
        return p != null ? p.parent : null;
    }

    // 6. Brother(p) - возвращает брата
    public Node Brother(Node p) {
        if (p == null || p.parent == null) return null;
        return IsLeft(p) ? p.parent.right : p.parent.left;
    }

    // 7. IsLeft(p) - проверяет, является ли узел левым потомком
    public boolean IsLeft(Node p) {
        return p != null && p.parent != null && p.parent.left == p;
    }

    // 8. IsRight(p) - проверяет, является ли узел правым потомком
    public boolean IsRight(Node p) {
        return p != null && p.parent != null && p.parent.right == p;
    }

    // Вспомогательный метод для поиска узла
    private Node findNode(Node node, String v) {
        if (node == null) return null;
        if (node.value.equals(v)) return node;
        Node left = findNode(node.left, v);
        if (left != null) return left;
        return findNode(node.right, v);
    }

    // Вставка элемента в дерево
    public void insert(String value) {
        root = insertRec(root, null, value);
    }

    private Node insertRec(Node node, Node parent, String value) {
        if (node == null) {
            Node newNode = new Node(value);
            newNode.parent = parent;
            return newNode;
        }

        if (value.compareTo(node.value) < 0) {
            node.left = insertRec(node.left, node, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insertRec(node.right, node, value);
        }

        return node;
    }

    private Node deleteNode(Node root, Node nodeToDelete) {
        if (root == null) return null;

        if (nodeToDelete.value.compareTo(root.value) < 0) {
            root.left = deleteNode(root.left, nodeToDelete);
            if (root.left != null) root.left.parent = root;
        } else if (nodeToDelete.value.compareTo(root.value) > 0) {
            root.right = deleteNode(root.right, nodeToDelete);
            if (root.right != null) root.right.parent = root;
        } else {
            // Узел для удаления найден
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // У узла есть два потомка - находим минимальный в правом поддереве
            root.value = minValue(root.right);
            root.right = deleteNode(root.right, new Node(root.value));
            if (root.right != null) root.right.parent = root;
        }
        return root;
    }

    private String minValue(Node node) {
        String minValue = node.value;
        while (node.left != null) {
            minValue = node.left.value;
            node = node.left;
        }
        return minValue;
    }

    // Удаление узлов с заданными словами (улучшенный вывод)
    public void removeNodesWithWords(String[] wordsToRemove) {
        System.out.println("\nНачинаем удаление узлов:");
        for (String word : wordsToRemove) {
            Node nodeToRemove = Addr(word);
            if (nodeToRemove != null) {
                String position;
                if (nodeToRemove == root) {
                    position = "корнем";
                } else {
                    position = IsLeft(nodeToRemove) ? "левым" : "правым";
                }

                System.out.println("\nУдаляем узел '" + word +
                        "' (" + position + " потомок)");

                root = deleteNode(root, nodeToRemove);
                printTree();
            } else {
                System.out.println("\nУзел '" + word + "' не найден в дереве");
            }
        }
    }

    // Вывод дерева с нумерацией шагов
    private static int stepCounter = 1;
    public void printTree() {
        if (root == null) {
            System.out.println("Шаг " + stepCounter++ + ": Дерево пусто");
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        System.out.print("Шаг " + stepCounter++ + ": Дерево (обход в ширину): [");

        boolean firstElement = true;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (!firstElement) {
                System.out.print(", ");
            } else {
                firstElement = false;
            }
            System.out.print("\"" + node.value + "\"");
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        System.out.println("]");
    }
}

public class Main {
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        // Вставляем элементы в дерево
        String[] elements = {"hello", "world", "java", "binary", "tree", "search", "algorithm"};
        System.out.println("Строим дерево поиска со строками:");
        for (String elem : elements) {
            tree.insert(elem);
        }

        System.out.println("\nИсходное дерево:");
        tree.printTree();

        // Удаляем узлы с заданными словами
        String[] wordsToRemove = {"java", "world", "search"};
        tree.removeNodesWithWords(wordsToRemove);
    }
}