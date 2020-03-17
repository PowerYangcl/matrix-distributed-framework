package com.matrix.sxt.e07;

public class ReadWriteLock {
    public static void main(String[] args) {
        DemoCache myCaChe = new DemoCache();
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> { myCaChe.put(temp + "", temp); }, String.valueOf(i)).start();
        }
        for (int i = 6; i <= 10; i++) {
            int temp = i - 5;
            new Thread(() -> { myCaChe.get(temp + ""); }, String.valueOf(i)).start();
        }
    }
}
































