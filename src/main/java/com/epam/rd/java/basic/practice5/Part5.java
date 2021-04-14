package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class Part5 {

    public static void main(final String[] args) {
        try {
            writeFile("part5.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String fileName) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(fileName), "rw");
        AtomicInteger indexForThread = new AtomicInteger();
        indexForThread.set(0);
        for (int i = 0; i < 10; ++i) {
            int threadIndex = i;
            Thread thread = new Thread(() -> {
                StringBuilder stringBuffer = new StringBuilder();
                for (int j = 0; j < 20; ++j) {
                    stringBuffer.append(threadIndex);
                }
                stringBuffer.append(System.lineSeparator());
                byte[] bytes = stringBuffer.toString().getBytes(StandardCharsets.UTF_8);
                try {
                    randomAccessFile.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    randomAccessFile.seek((long) indexForThread.get() + bytes.length);
                    indexForThread.set(indexForThread.get() + bytes.length);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        randomAccessFile.close();
    }

}
