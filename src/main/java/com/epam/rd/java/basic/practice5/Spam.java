package com.epam.rd.java.basic.practice5;

public class Spam {

    private final Thread[] threads;
    private final String[] strings;
    private final int[] time;

    public static boolean isInterrupted = false;
    public static String line=null;

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
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] messages = new String[]{"@@@", "bbbbbbb"};
        int[] times = new int[]{333, 222};
        Spam spam = new Spam(messages, times);
        spam.start();
        while (!isInterrupted) {
            if ("stop".equals(line)) {
                spam.stop();
                isInterrupted = true;
                break;
            }
        }


    }
}
