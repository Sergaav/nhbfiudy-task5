package com.epam.rd.java.basic.practice5;

import java.io.*;

public class Part2 {

    public static void main(final String[] args) {
        InputStream consolIn = System.in;
        InputStream is = new ByteArrayInputStream("stop".getBytes());
        System.setIn(is);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Thread t = new Thread(() -> {
            Spam.main(null);
        });
        t.start();
        try {
            while (!Spam.isInterrupted) {
                is.reset();
                Thread.sleep(2000);
                Spam.line = bufferedReader.readLine();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
