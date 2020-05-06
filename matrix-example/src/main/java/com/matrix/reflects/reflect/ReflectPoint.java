package com.matrix.reflects.reflect;

public class ReflectPoint {
	
	private int x;//私有的
	public int y;//公共的
	public String str = "jiangwei";
	public String str1 = "jiangzhi";
	
	public ReflectPoint(){
	}
	
	public ReflectPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		super.toString();
		return "ReflectPoint [x=" + x + ", y=" + y + ", str=" + str + ", str1="
				+ str1 + "]";
	}
	

}
