package com.matrix.sxt.e04;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicRefDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<User>();
        User bzh = new User("巴扎黑" , 22);
        User ptq = new User("皮条强" , 44);

        atomicReference.set(bzh);

        System.out.println("比较替换后的结果：" + atomicReference.compareAndSet(bzh, ptq) + "  当前用户为：" + atomicReference.get().getName());
        System.out.println("比较替换后的结果：" + atomicReference.compareAndSet(bzh, ptq) + "  当前用户为：" + atomicReference.get().getName());
        System.out.println("比较替换后的结果：" + atomicReference.compareAndSet(ptq, bzh) + "  当前用户为：" + atomicReference.get().getName());
    }
}














































