package com.epam.rd.java.basic.practice5;

import java.io.*;
import java.util.concurrent.CountDownLatch;

public class Part4 {
    public static void main(final String[] args) {
        long start = System.currentTimeMillis();
        int[][] matrix = new int[0][];
        try {
            matrix = readInput("part4.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int totalThreads = matrix.length;
        int[] results = new int[totalThreads];
        CountDownLatch end = new CountDownLatch(totalThreads);

        for (int i = 0; i < totalThreads; ++i) {
            int threadIndex = i;
            int[][] finalMatrix = matrix;
            new Thread(() -> {
                results[threadIndex] = findMax(finalMatrix[threadIndex]);
                end.countDown();
            }).start();
        }
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int max = findMax(results);
        System.out.println(max);
        System.out.println(System.currentTimeMillis() - start);
        long start1 = System.currentTimeMillis();
        int max2 = -1;
        for (int[] ints : matrix) {
            int temp = findMax(ints);
            if (temp > max2) {
                max2 = temp;
            }
        }
        System.out.println(max2);
        System.out.println(System.currentTimeMillis() - start1);


    }

    public static int findMax(int[] ints) {
        int max = -1;
        for (int anInt : ints) {
            if (max == -1 || max < anInt) {
                max = anInt;
            }
        }
        return max;
    }

    public static int[][] readInput(String fileName) throws IOException {
        String string;
        int[][] out = new int[4][100];
        int i = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            while (bufferedReader.ready()) {
                string = bufferedReader.readLine();
                String[] temp = string.split(" ");
                for (int j = 0; j < temp.length; ++j) {
                    out[i][j] = Integer.parseInt(temp[j]);
                }
                ++i;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return out;
    }

}
