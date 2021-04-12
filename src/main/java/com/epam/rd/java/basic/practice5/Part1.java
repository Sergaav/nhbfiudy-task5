package com.epam.rd.java.basic.practice5;

public class Part1 extends Thread {

    @Override
    public void run() {
        printThreadName();
    }

    public static void main(String[] args) {
        Thread t = new Part1();
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                printThreadName();
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void printThreadName() {
        for (int i = 0; i < 4; ++i) {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
