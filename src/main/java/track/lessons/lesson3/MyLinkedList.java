package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List implements Stack, Queue {

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    private Node head;

    @Override
    void add(int item) {
        if (size == 0) {
            head = new Node(null, null, item);
        } else {
            Node newElement = new Node(head, null, item);
            head.next = newElement;
            head = newElement;
        }
        ++size;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }
        Node currentElement = head;
        for (int i = size - 1; i > idx; --i) {
            currentElement = currentElement.prev;
        }
        if (currentElement.prev != null) {
            currentElement.prev.next = currentElement.next;
        }
        if (currentElement.next != null) {
            currentElement.next.prev = currentElement.prev;
        }
        --size;
        return currentElement.val;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }
        Node currentElement = head;
        for (int i = size - 1; i > idx; --i) {
            currentElement = currentElement.prev;
        }
        return currentElement.val;
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        return remove(size - 1);
    }

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeu() {
        return remove(0);
    }

}
