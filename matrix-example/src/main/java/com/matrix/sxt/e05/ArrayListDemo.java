package com.matrix.sxt.e05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArrayListDemo {

	public static void main(String[] args) {
		List<String> list = new CopyOnWriteArrayList<>();
		for (int i = 1; i <= 30; i++) {
			new Thread(() -> {
				list.add( Thread.currentThread().getName() );
				System.out.println(list);
			}, String.valueOf("T-" +i)).start();
		}
	}

}





//List<String> list= new CopyOnWriteArrayList<>();
//List<String> list= new CopyOnWriteArrayList<>();