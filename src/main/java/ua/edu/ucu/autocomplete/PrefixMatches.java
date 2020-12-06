package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class PrefixMatches {

    private static final int MIN_PREFIX = 2;
    private final Trie trie = new RWayTrie();

    public PrefixMatches() {
        super();
    }

    public PrefixMatches(Trie trie) {
        Iterable<String> words = trie.words();
        for (String word : words) {
            this.load(word);
        }
    }

    private void add(String word) {
        if (word.length() > MIN_PREFIX) {
            this.trie.add(new Tuple(word, word.length()));
        }
    }

    private void addLine(String line) {
        Arrays.stream(line.split(" ")).forEach(this::add);
    }

    public int load(String... strings) {
        int oldSize = size();
        for (String line : strings) {
            if (line.contains(" ")) {
                addLine(line);
            } else {
                add(line);
            }
        }
        return size() - oldSize;
    }

    public boolean contains(String word) {
        if (word.length() <= MIN_PREFIX) {
            return false;
        }
        return trie.contains(word);
    }

    public boolean delete(String word) {
        if (word.length() <= MIN_PREFIX) {
            return false;
        }
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < MIN_PREFIX) {
            return null;
        }
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < MIN_PREFIX || k < 0) {
            return null;
        }
        Iterable<String> allWords = this.wordsWithPrefix(pref);
        HashMap<Integer, HashSet<String>> wordMap = new HashMap<>();
        for (String word : allWords) {
            wordMap.computeIfAbsent(word.length(), k1 -> new HashSet<>());
            wordMap.get(word.length()).add(word);
        }
        HashSet<String> requiredWords = new HashSet<>();
        List<Integer> keys = wordMap.keySet().stream().sorted().collect(
                Collectors.toList()
        );
        for (int key : keys) {
            if (k == 0) {
                break;
            }
            requiredWords.addAll(wordMap.get(key));
            k--;
        }
        return requiredWords;
    }

    public int size() {
        return this.trie.size();
    }
}
