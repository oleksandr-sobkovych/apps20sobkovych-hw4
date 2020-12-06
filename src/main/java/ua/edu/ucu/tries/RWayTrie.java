package ua.edu.ucu.tries;

import ua.edu.ucu.helper.collections.Queue;

public class RWayTrie implements Trie {
    private static final int WEIGHT_FLAG = 0;
    private static final int R = 256;
    private int size = 0;
    private Node root;

    private static class Node {
        private int weight;
        private final Node[] next = new Node[R];
    }
    
    @Override
    public void add(Tuple word) {
        root = add(root, word.term, word.weight, 0);
    }

    private Node add(Node current, String term, int weight, int depth) {
        if (current == null) {
            current = new Node();
        }
        if (depth == term.length()) {
            if (current.weight == WEIGHT_FLAG) {
                this.size++;
            }
            current.weight = weight;
            return current;
        }
        char c = term.charAt(depth);
        current.next[c] = add(current.next[c], term, weight, depth+1);
        return current;
    }

    @Override
    public boolean contains(String word) {
        Node found = this.search(root, word);
        if (found == null) {
            return false;
        }
        return found.weight != WEIGHT_FLAG;
    }

    @Override
    public boolean delete(String word) {
        Node found = this.search(root, word);
        if (found == null || found.weight == WEIGHT_FLAG) {
            return false;
        }
        found.weight = WEIGHT_FLAG;
        this.size--;
        return true;
    }

    private Node search(Node start, String word) {
        Node pointer = start;
        for (int i = 0; i < word.length(); i++) {
            if (pointer == null) {
                return null;
            }
            pointer = pointer.next[word.charAt(i)];
        }
        return pointer;
    }
    
    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        Queue<String> q = new Queue<>();
        collect(search(root, pref), pref, q);
        return q;
    }

    private void collect(Node current, String pref, Queue<String> q) {
        if (current == null) {
            return;
        }
        if (current.weight != WEIGHT_FLAG) {
            q.enqueue(pref);
        }
        for (char c = 0; c < R; c++) {
            collect(current.next[c], pref + c, q);
        }
    }

    @Override
    public int size() {
        return this.size;
    }
}