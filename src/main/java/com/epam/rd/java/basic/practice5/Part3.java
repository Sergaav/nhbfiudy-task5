package com.epam.rd.java.basic.practice5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Part3 {

    private int counter;
    private int counter2;
    private AtomicInteger iterations;

    private final int numberOfThreads;
    private final int numberOfIterations;

    public Part3(int numberOfThreads, int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
        this.numberOfThreads = numberOfThreads;
        iterations=new AtomicInteger(numberOfIterations);
    }


    public static void main(final String[] args) {
        Part3 part3 = new Part3(3, 10);
       part3.compare();
        part3.reset();
        part3.compareSync();
    }

    public void reset() {
        counter = 0;
        counter2 = 0;
        iterations=new AtomicInteger(numberOfIterations);
    }

    public void compare() {
        CountDownLatch end = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(this::printingAndIncrementing).start();
            end.countDown();
        }
        try {
            end.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void printingAndIncrementing(){
        while (iterations.get() > 0) {
            System.out.println(counter == counter2);
            counter++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
            counter2++;
            iterations.decrementAndGet();
        }
    }


    public void compareSync() {
        CountDownLatch end = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(() -> {
                synchronized (this) {
                    printingAndIncrementing();
                }
            }).start();
            end.countDown();
        }
        try {
            end.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
