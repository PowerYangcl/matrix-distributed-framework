package com.matrix.sxt.e05;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class MapNotSafe {

	public static void main(String[] args) {
		Map<String , String> map = new ConcurrentHashMap<String, String>();
		for (int i = 1; i <= 30; i++) {
			new Thread(() -> {
				String name = Thread.currentThread().getName();
				map.put(name , name.split("-")[1]);
				System.out.println(map);
			}, String.valueOf("T-" +i)).start();
		}
		
	}
	
	
	public static void main2(String[] args) {
		Map<String , String> map = new HashMap<String, String>();
		for (int i = 1; i <= 30; i++) {
			new Thread(() -> {
				String name = Thread.currentThread().getName();
				map.put(null , null);
				System.out.println(map);
			}, String.valueOf("T-" +i)).start();
		}
		
	}
	
	public static void main3(String[] args) {
		Map<String , String> map = new Hashtable<String, String>();
		for (int i = 1; i <= 30; i++) {
			new Thread(() -> {
				String name = Thread.currentThread().getName();
				map.put(name , name.split("-")[1]);
				System.out.println(map);
			}, String.valueOf("T-" +i)).start();
		}
	}
	
}

















