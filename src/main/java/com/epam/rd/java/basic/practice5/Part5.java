package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Part5 {

    public static void main(final String[] args) {
        try {
            writeFile("part5.txt");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        readAndPrintFile("part5.txt");
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
                    System.err.println(e.getMessage());
                }
                try {
                    randomAccessFile.seek((long) indexForThread.get() + bytes.length);
                    indexForThread.set(indexForThread.get() + bytes.length);

                } catch (IOException e) {
                    System.err.println(e.getMessage());
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

    public static void readAndPrintFile(String fileName){
        String res = null;
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            res = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.print(res);
    }

}
