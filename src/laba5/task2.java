package laba5;

import java.util.HashMap;
import java.util.Map;

class SuffixTreeNode {
    private final Map<Character, SuffixTreeNode> children;
    private boolean isEnd;

    public SuffixTreeNode() {
        this.children = new HashMap<>();
        this.isEnd = false;
    }

    public Map<Character, SuffixTreeNode> getChildren() {
        return children;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
}

class SuffixTree {
    private final SuffixTreeNode root;
    private final String text;

    public SuffixTree(String text) {
        this.root = new SuffixTreeNode();
        this.text = text;
        buildTree();
    }

    private void buildTree() {
        for (int i = 0; i < text.length(); i++) {
            SuffixTreeNode current = root;
            for (int j = i; j < text.length(); j++) {
                char c = text.charAt(j);
                current.getChildren().putIfAbsent(c, new SuffixTreeNode()); // Проверяет, есть ли символ c в дочерних узлах current
                current = current.getChildren().get(c);
            }
            current.setEnd(true);
        }
    }

    public boolean contains(String pattern) {
        SuffixTreeNode current = root;
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (!current.getChildren().containsKey(c)) {
                return false;
            }
            current = current.getChildren().get(c);
        }
        return true;
    }
}



public class task2 {
    public static void main(String[] args) {
        String[] testTexts = {
            "banana$",
            "hello, world!",
            "abracadabra"
        };

        for (String text : testTexts) {
            System.out.println("\nTesting text: '" + text + "'");
            SuffixTree tree = new SuffixTree(text);

            String[] patterns = {
                text.substring(1, 4),  // случайный подстроки
                "xyz",                 // заведомо отсутствующий
                text.substring(0, 3)   // префикс
            };

            for (String pattern : patterns) {
                boolean result = tree.contains(pattern);
                System.out.printf("Pattern '%s' %s in text%n", pattern, result ? "exists" : "does not exist");
            }
        }
    }
}
