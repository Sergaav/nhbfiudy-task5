package com.epam.rd.java.basic.practice5;

public class Part3 {

    private int counter;
    private int counter2;
    private final int numberOfThreads;
    private final int numberOfIterations;

    public Part3(int numberOfThreads, int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
        this.numberOfThreads = numberOfThreads;
    }


    public static void main(final String[] args) {
        new Part3(2, 10).compare();
        new Part3(2,10).compareSync();


    }

    public void compare() {
        for (int i = 0; i < numberOfThreads; ++i) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < numberOfIterations; ++j) {
                    System.out.println(counter + " = " + counter2);
                    ++counter;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    ++counter2;
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void compareSync() {
        for (int i = 0; i < numberOfThreads; ++i) {
            new Thread(() -> {
                for (int j = 0; j < numberOfIterations; ++j) {
                    synchronized (this) {
                        System.out.println(counter + " = " + counter2);
                        ++counter;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                        ++counter2;
                    }
                }
            }).start();
        }
    }
}
