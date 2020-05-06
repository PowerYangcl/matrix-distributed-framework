package com.matrix.sxt.e16;

import java.util.ArrayList;
import java.util.List;

// -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC
// -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC
// -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC
// -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC
public class GcSerialDemo {
	public static void main(String[] args) {
		int i = 0;
		List<String> list = new ArrayList<String>();
		try {
			while(true) {
				list.add(String.valueOf(++ i).intern());
			}
		} catch (Throwable e) { // Exception和Error的上层接口
			e.printStackTrace();
		}
	}
}
