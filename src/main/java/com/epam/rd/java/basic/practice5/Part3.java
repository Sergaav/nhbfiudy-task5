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
        Part3 part3 = new Part3(3, 10);
        part3.compare();
        part3.reset();
        part3.compareSync();
    }

    public void reset() {
        counter = 0;
        counter2 = 0;
    }

    public void compare() {
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(() -> {
                while (counter < numberOfIterations) {
                    System.out.println(counter + " = " + counter2);
                    counter++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    counter2++;
                }
            });
            thread.start();
            threads[i] = thread;
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }


    public void compareSync() {
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (counter < numberOfIterations) {
                        synchronized (this) {
                            System.out.println(counter == counter2);
                            counter++;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                            counter2++;
                        }
                    }
                }
            }).start();
        }
    }
}
