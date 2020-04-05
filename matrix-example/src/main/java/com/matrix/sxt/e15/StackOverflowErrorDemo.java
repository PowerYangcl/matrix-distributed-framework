package com.matrix.sxt.e15;

public class StackOverflowErrorDemo {

	public static void main(String[] args) {
		stackOverflowError();
	}

	private static void stackOverflowError() {
		stackOverflowError();
	}
}
