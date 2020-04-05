package com.matrix.sxt.e15;

import java.util.ArrayList;
import java.util.List;

// -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5M

public class GcOverheadDemo {
	public static void main(String[] args) {
		int i = 0;
		List<String> list = new ArrayList<String>();
		try {
			while(true) {
				list.add(String.valueOf(++ i).intern());
			}
		} catch (Throwable e) { // Exception和Error的上层接口
			System.out.println("$$$$$$$$$$$$$$$$$$$$ i = " + i);
			e.printStackTrace();
		}
	}
}
