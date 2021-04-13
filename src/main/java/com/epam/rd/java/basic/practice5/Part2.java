package com.epam.rd.java.basic.practice5;

import java.io.*;

public class Part2 {

    public static void main(final String[] args) {
        InputStream consolIn = System.in;
        InputStream is = new MyInputStream(new ByteArrayInputStream(System.lineSeparator().getBytes()));
        System.setIn(is);
        Thread t = new Thread(() -> Spam.main(null));
        t.start();
        try {
            t.join();
            is.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            t.interrupt();
            Thread.currentThread().interrupt();
        } finally {
            System.setIn(consolIn);
        }

    }


    public static class MyInputStream extends InputStream {
        protected InputStream is;

        public MyInputStream(InputStream is) {
            this.is = is;
        }

        @Override
        public int read() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            return -1;
        }
    }
}