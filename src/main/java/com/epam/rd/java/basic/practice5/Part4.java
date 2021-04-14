package com.epam.rd.java.basic.practice5;

import java.io.*;
import java.util.concurrent.CountDownLatch;

public class Part4 {

    public static void main(final String[] args) {

        long start = System.currentTimeMillis();
        try {
            System.out.println(searchMaxValueMulti(readInput("part4.txt")));
        } catch (InterruptedException | IOException e) {
            System.err.println(e);
            Thread.currentThread().interrupt();
        }
        System.out.println(System.currentTimeMillis()-start);

        long start1 = System.currentTimeMillis();
        try {
            System.out.println(searchMaxValueSingle(readInput("part4.txt")));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(System.currentTimeMillis()-start1);


    }

    public static int searchMaxValueMulti(int[][] matrix) throws InterruptedException {
        int column = matrix.length;
        int[] results = new int[column];
        CountDownLatch end = new CountDownLatch(column);
        for (int i = 0; i < column; i++) {
            int threadIndex = i;
            new Thread(
                    () -> {
                        int max = -1;
                        for (int k = 0; k < matrix[threadIndex].length; k++) {
                            if (max == -1 || max < matrix[threadIndex][k]) {
                                max = matrix[threadIndex][k];
                            }
                        }
                        results[threadIndex] = max;
                        end.countDown();
                    }
            ).start();
        }
        end.await();
        int max = results[0];
        for (int k = 1; k < results.length; k++) {
            if (max < results[k]) {
                max = results[k];
            }
        }
        return max;
    }

    public static int searchMaxValueSingle(int[][] matrix) {
        int column =matrix[0].length;
        int max = matrix[0][0];
        for (int[] ints : matrix) {
            for (int b = 0; b < column; b++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                    Thread.currentThread().interrupt();
                }
                if (ints[b] > max) {
                    max = ints[b];
                }
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
           System.err.println(e.getMessage());
        }
        return out;
    }

}
