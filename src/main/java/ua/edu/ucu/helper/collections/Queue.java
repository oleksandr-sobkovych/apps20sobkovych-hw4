package ua.edu.ucu.helper.collections;

import ua.edu.ucu.helper.exceptions.EmptyQueueException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Queue<T> implements Iterable<T> {
    private final LinkedList<T> list;

    public Queue() {
        this.list = new LinkedList<>();
    }

    public T peek() {
        try {
            return this.list.getLast();
        } catch (NoSuchElementException e) {
            throw new EmptyQueueException("no elements to peek at");
        }
    }

    public T dequeue() {
        try {
            return this.list.removeLast();
        } catch (NoSuchElementException e) {
            throw new EmptyQueueException("no elements to dequeue");
        }
    }

    public void enqueue(T e) {
        this.list.addFirst(e);
    }

    @Override
    public String toString() {
        return "Queue[" + list.toString() + "]";
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.list.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.list.spliterator();
    }
}
