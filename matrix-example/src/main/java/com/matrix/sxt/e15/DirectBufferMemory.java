package com.matrix.sxt.e15;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class DirectBufferMemory {
	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		long maxDirectMemory = sun.misc.VM.maxDirectMemory()  / 1024 / 1024;
		System.out.println("系统最大max direct memory = " + maxDirectMemory + " M");  // 默认系统最大max direct memory = 1808 M，本机系统8G内存，约1/4
		try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}
		// -XX:MaxDirectMemorySize=5m 我们配置为5MB启动程序，但代码中实际分配6MB空间，从而引发Direct buffer memory错误
		ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
	}
}
