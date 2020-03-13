package com.matrix.sxt.e05;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetNotSafe {
	
	
	public static void main(String[] args) {
		
		Set<String> set = new CopyOnWriteArraySet<String>();
		
		for (int i = 1; i <= 30; i++) {
			new Thread(() -> {
				set.add( Thread.currentThread().getName() );
				System.out.println(set);
			}, String.valueOf("T-" +i)).start();
		}
	}
	
	
	
}













