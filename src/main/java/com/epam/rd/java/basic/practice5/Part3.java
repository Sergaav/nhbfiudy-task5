package com.epam.rd.java.basic.practice5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Part3 {

    private int counter;
    private int counter2;
    private AtomicInteger iterations;

    private final int numberOfThreads;
    private final int numberOfIterations;

    public Part3(int numberOfThreads, int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
        this.numberOfThreads = numberOfThreads;
        iterations = new AtomicInteger(numberOfIterations);
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
        iterations = new AtomicInteger(numberOfIterations);
    }

    public void compare() {
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; ++i) {
            Thread t = new Thread(() -> {
                latch.countDown();
                for (int j = 0; j < numberOfIterations; ++j) {
                    System.out.println(counter + " = " + counter2);
                    ++counter;
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                    ++counter2;
                }
            });
            threads[i] = t;
            t.start();

        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }


    }


    public void compareSync() {
        CountDownLatch end = new CountDownLatch(numberOfThreads);
        ReentrantLock lock = new ReentrantLock();
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(() -> {
                    lock.lock();
                    for (int j=0;j<numberOfIterations;j++) {
                        System.out.println(counter == counter2);
                        counter++;
                        try {
                            Thread.sleep(98);
                        } catch (InterruptedException e) {
                            System.err.println(e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                        counter2++;
                    }
                    lock.unlock();
            });
            threads[i] = thread;
            thread.start();
            end.countDown();
        }
        try {
            end.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.print(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

    }
}
