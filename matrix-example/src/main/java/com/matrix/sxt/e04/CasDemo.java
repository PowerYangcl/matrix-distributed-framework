package com.matrix.sxt.e04;

import java.util.concurrent.atomic.AtomicInteger;

public class CasDemo {
    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger(5);
        ai.getAndIncrement();
        boolean flag1 = ai.compareAndSet(6, 2020);
        boolean flag2 = ai.compareAndSet(6, 289);
        System.out.println(flag1 + " 当前值：" + ai.get());
        System.out.println(flag2 + " 当前值：" + ai.get());
    }
}
