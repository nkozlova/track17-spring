package track.BlockingQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class MyBlockingQueue<T> implements BlockingQueue<T> {

    private Queue<T> queue;
    private ReentrantLock full;
    private ReentrantLock empty;
    private ReentrantLock lock;

    public MyBlockingQueue() {
        queue = new LinkedList<T>();
        full = new ReentrantLock();
        empty = new ReentrantLock();
        lock = new ReentrantLock();
    }

    @Override
    public void put(T elem) {
        full.lock();
        lock.lock();
        full.unlock();
        queue.add(elem);
        empty.unlock();
        if (queue.size() == SIZE) {
            full.lock();
        }
        lock.unlock();
    }

    @Override
    public boolean offer(T elem) {
        if (full.tryLock()) {
            lock.lock();
            full.unlock();
            queue.add(elem);
            empty.unlock();
            if (queue.size() == SIZE) {
                full.lock();
            }
            lock.unlock();
            return true;
        }
        return false;
    }

    @Override
    public T take() {
        empty.lock();
        lock.lock();
        empty.unlock();
        T elem = queue.peek();
        full.unlock();
        if (queue.size() == 0) {
            empty.lock();
        }
        lock.unlock();
        return elem;
    }

    @Override
    public T poll(int timeout) {
        FutureTask<T> task = new FutureTask<>(this::take);
        task.run();
        try {
            return task.get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return null;
        }
    }
}