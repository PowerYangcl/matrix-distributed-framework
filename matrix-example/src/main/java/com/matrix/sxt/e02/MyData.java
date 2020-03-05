package com.matrix.sxt.e02;

import java.util.concurrent.atomic.AtomicInteger;

public class MyData {
	// 变量未添加volatile关键字，没有可见性
	public volatile int number = 0;
	public AtomicInteger atomicInteger = new AtomicInteger();
	
	public void addNumber() {
		this.number ++;
	}
	public void atomicAdd() {
		atomicInteger.getAndIncrement();
	}
	
	public int getNumber() {
		return number;
	}
	public AtomicInteger getAtomicValue() {
		return atomicInteger;
	}
}
