package com.matrix.sxt.e14;

import java.util.concurrent.TimeUnit;

public class HelloGc {
    public static void main(String[] args) {

        for(String s : args){
            System.out.println("arg = " + s);
        }

        System.out.println("GC Test Begin ..");
//        try { TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);} catch (InterruptedException e) {e.printStackTrace();}
    }
}
