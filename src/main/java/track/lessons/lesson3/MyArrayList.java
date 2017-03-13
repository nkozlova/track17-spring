package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    private int []array;
    private int capacity;

    public MyArrayList() {
        capacity = 10;
        array = new int[capacity];

    }

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
    }

    @Override
    void add(int item) {
        if (capacity == 0) {
            capacity = 2;
            array = new int[capacity];
        }
        if (size >= capacity - 1) {
            capacity *= 2;
            array = new int[capacity];
        }
        array[size] = item;
        ++size;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx < 0 || size <= idx) {
            throw new NoSuchElementException();
        }
        int answer = array[idx];
        for (int i = idx; i < size; ++i) {
            array[i] = array[i + 1];
        }
        --size;
        return answer;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx < 0 || size <= idx) {
            throw new NoSuchElementException();
        }
        return array[idx];
    }

}
