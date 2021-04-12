package com.epam.rd.java.basic.practice5;

public class Demo {

    public static void main(String[] args) {
        Part1.main(args);
        Part2.main(args);
        Part3.main(args);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Util.main(args);
        Part4.main(args);

    }

}
