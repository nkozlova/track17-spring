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

    static final int DEFAULT_CAPACITY = 10;

    private int []array;
    private int capacity;

    public MyArrayList() {
        capacity = DEFAULT_CAPACITY;
        array = new int[DEFAULT_CAPACITY];
    }

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
    }

    @Override
    void add(int item) {
        if (capacity == 0) {
            capacity = DEFAULT_CAPACITY;
            array = new int[capacity];
        }
        if (size >= capacity - 1) {
            capacity *= 2;
            int []newArray = new int[capacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
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
            System.arraycopy(array, idx + 1, array, idx, size - idx - 1);
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
