package com.epam.rd.java.basic.practice5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class Util {

    public static void main(String[] args) {
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
            e.printStackTrace();
        }


    }
}
