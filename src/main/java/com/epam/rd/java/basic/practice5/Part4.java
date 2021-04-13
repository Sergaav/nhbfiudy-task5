package com.epam.rd.java.basic.practice5;

import java.io.*;

public class Part4 {
    public static void main(final String[] args) {
        long start = System.currentTimeMillis();
        int[][] matrix = new int[4][100];
        try {
            matrix = readInput("part4.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int[] result = {0};
        for (int i = 0; i < 4; i++) {
            int[] finalMatrix = matrix[i];
            Thread thread = new Thread(() -> {
                for (int i1 = 0; i1 < 100; i1++) {
                    if (finalMatrix[i1] > result[0]) {
                        result[0] = finalMatrix[i1];
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(result[0]);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);


        long start1 = System.currentTimeMillis();
        int[][] matrix1 = new int[4][100];
        try {
            matrix1 = readInput("part4.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int result1 = 0;
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 100; k++) {

                if (matrix1[j][k] > result1) {

                    result1 = matrix1[j][k];
                }
            }
        }
        System.out.println(result1);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start1);

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
