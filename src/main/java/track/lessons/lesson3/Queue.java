package track.lessons.lesson3;

// Очередь - структура данных, удовлетворяющая правилу First IN First OUT

public interface Queue {

    void enqueue(int value); // поместить элемент в очередь

    int dequeu(); // вытащить первый элемент из очереди

}