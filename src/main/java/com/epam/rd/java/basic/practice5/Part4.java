package com.epam.rd.java.basic.practice5;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Part4 {
    public static void main(final String[] args) {
        long start = System.currentTimeMillis();
        int[][] matrix = new int[4][100];
        try {
            matrix = readInput("part4.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int totalThreads = matrix.length;
        int[] results = new int[totalThreads];
        ExecutorService executorService = Executors.newFixedThreadPool(totalThreads);
        for (int i = 0; i < totalThreads; ++i) {
            int threadIndex = i;
            int[][] finalMatrix = matrix;
            executorService.submit(() -> results[threadIndex] = findMax(finalMatrix[threadIndex]));
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        int max = findMax(results);
        System.out.println(max);
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        long start1 = System.currentTimeMillis();
        int[][] matrix1 = new int[4][100];
        try {
            matrix1 = readInput("part4.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int max2 = -1;
        for (int[] ints : matrix1) {
            int temp = findMax(ints);
            if (temp > max2) {
                max2 = temp;
            }
        }
        System.out.println(max2);
        System.out.println(System.currentTimeMillis() - start1);
        executorService.shutdown();


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
