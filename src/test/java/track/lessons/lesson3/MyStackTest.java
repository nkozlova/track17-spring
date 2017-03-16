package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;


public class MyStackTest {

    @Test
    public void testPushsAndPop() {
        Stack stack = new MyLinkedList();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        Assert.assertEquals(9, stack.pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testManyPops() {
        Stack stack = new MyLinkedList();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        for (int i = 0; i < 10; i++) {
            stack.pop();
        }
        stack.pop();
    }

    @Test
    public void stackPushPop() throws Exception {
        Stack stack = new MyLinkedList();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        Assert.assertEquals(3, stack.pop());
        Assert.assertEquals(2, stack.pop());
        Assert.assertEquals(1, stack.pop());
    }
}