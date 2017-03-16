package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;


public class MyQueueTest {

    @Test
    public void testEnqueuesAndDequeue() {
        Queue queue = new MyLinkedList();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }

        Assert.assertEquals(0, queue.dequeu());
    }

    @Test(expected = NoSuchElementException.class)
    public void testManyDequeues() {
        Queue queue = new MyLinkedList();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 10; i++) {
            queue.dequeu();
        }
        queue.dequeu();
    }

    @Test
    public void queueEnqueueDequeue() throws Exception {
        Queue queue = new MyLinkedList();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        Assert.assertEquals(1, queue.dequeu());
        Assert.assertEquals(2, queue.dequeu());
        Assert.assertEquals(3, queue.dequeu());
    }
}