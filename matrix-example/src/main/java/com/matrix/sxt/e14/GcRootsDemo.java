package com.matrix.sxt.e14;

import com.matrix.sxt.e12.Task1001Position;
import com.matrix.sxt.e13.HoldThread;

public class GcRootsDemo {
	private byte[] arr = new byte[100 * 1024 * 1024];
	
	private static HoldThread holdThread;		// 2)	方法区中的类静态属性引用的对象。强引用，不容易被回收，使用需谨慎。
	private static final Task1001Position task = new Task1001Position("" , "" , null);  // 3)	方法区中常量引用的对象。强引用，不容易被回收，使用需谨慎。
	
	public void functions(String str) {		// 1) 虚拟机栈方法要放到栈里，g1就是虚拟机栈中引用的对象
		GcRootsDemo gcRootsDemo = new GcRootsDemo();
		System.gc();
	}
	
	public static void main(String[] args) {
		new GcRootsDemo().functions("aaaa");  // 当被其他方法调用时，gcRootsDemo对象可达
	}
}
