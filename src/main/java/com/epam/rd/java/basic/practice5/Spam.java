package com.epam.rd.java.basic.practice5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Spam {

    private final Thread[] threads;
    private final String[] strings;
    private final int[] time;


    public Spam(String[] strings, int[] time) {
        this.time = time;
        this.strings = strings;
        threads = new Thread[strings.length];
    }

    public void start() {
        for (int i = 0; i < strings.length; ++i) {
            Thread thread = new Worker(strings[i], time[i]);
            threads[i] = thread;
            thread.start();
        }
    }

    public void stop() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        Thread.currentThread().interrupt();
    }

    private static class Worker extends Thread {
        String string;
        int interval;

        public Worker(String string, int interval) {
            this.string = string;
            this.interval = interval;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(string);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] messages = new String[]{"@@@", "bbbbbbb"};
        int[] times = new int[]{333, 222};
        Spam spam = new Spam(messages, times);
        spam.start();
        while (!Thread.currentThread().isInterrupted()) {
            int enter=0;
            try {
                enter = bufferedReader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (enter == -1) {
                spam.stop();
                break;
            }
        }


    }
}
