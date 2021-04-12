package com.epam.rd.java.basic.practice5;

import java.util.stream.IntStream;

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
       Part3 part3 =  new Part3(2, 10);
       part3.compare();
       part3.compareSync();




    }

    public void compare() {
        for (int i = 0; i < numberOfThreads; ++i) {
            Thread t = new Thread(() -> IntStream.range(0, numberOfIterations).forEach(j -> {
                    System.out.println(counter + " = " + counter2);
                    counter++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    counter2++;
                }));
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
