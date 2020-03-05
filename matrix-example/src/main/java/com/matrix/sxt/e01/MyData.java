package com.matrix.sxt.e01;

public class MyData {
	// 变量未添加volatile关键字，没有可见性
	public volatile int number = 0;
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
