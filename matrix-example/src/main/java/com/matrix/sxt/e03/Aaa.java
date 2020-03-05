package com.matrix.sxt.e03;

public class Aaa {
	
	int a = 0; 
    int b = 0; 
    int x = 0; 
    int y = 0; 
	
	
	
	@SuppressWarnings("unused")
	// 原始代码
	public void func(){
		int a = 0; 
	    int b = 0; 
	    int x = 0; 
	    int y = 0; 
	}
	
	@SuppressWarnings("unused")
	// 情况1：排序未发生改变
	public void func1(){
	    int x = 11;		// 语句1
	    int y = 12;		// 语句2
	    x = x + 5;		// 语句3
	    y = x * x;		// 语句4
	}
	
	
	@SuppressWarnings("unused")
	// 情况2：重排序
	public void func2(){
		int y = 12;		// 语句2
	    int x = 11;		// 语句1
	    x = x + 5;		// 语句3
	    y = x * x;		// 语句4
	}
	
	@SuppressWarnings("unused")
	// 情况3：重排序
	public void func3(){
	    int x = 11;		// 语句1
	    x = x + 5;		// 语句3
	    int y = 12;		// 语句2
	    y = x * x;		// 语句4
	}
	
	@SuppressWarnings("unused")
	// 其他导致：数据依赖性制约
	public void func4(){
		y = x * x;		// 语句4
	    int x = 11;		// 语句1
	    x = x + 5;		// 语句3
	    int y = 12;		// 语句2
	}
}















