package org.example;

import org.testng.annotations.Test;

public class SecondTest {
    @Test
    public void testA() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println("a");
    }

    @Test
    public void testB() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println("b");
        assert false;
    }

    @Test
    public void testC() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println("c");
    }

    @Test
    public void testD() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println("d");
    }

    @Test
    public void testE() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println("e");
    }
}
