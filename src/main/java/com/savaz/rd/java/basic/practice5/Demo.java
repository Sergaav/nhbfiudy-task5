package com.savaz.rd.java.basic.practice5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class Demo {

    public static void main(String[] args) {
        Part1.main(args);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
        Part2.main(args);

        Part3.main(args);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
        createPart4Txt();
        Part4.main(args);
        Part5.main(args);

    }

    public static void createPart4Txt (){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 100; ++j) {
                int x = new SecureRandom().nextInt(1000);
                stringBuilder.append(x).append(" ");
            }
            stringBuilder.append(System.lineSeparator());
        }
        try {
            Files.write(Paths.get("part4.txt"), stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
           System.err.println(e.getMessage());
        }
    }

}
