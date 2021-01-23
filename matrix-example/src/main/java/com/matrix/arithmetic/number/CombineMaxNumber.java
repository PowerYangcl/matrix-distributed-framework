package com.matrix.arithmetic.number;

/**
 * 给定数组，输出由数组中数字组成的最大数字。
 * 
 * @author rayeaster
 *
 */

public class CombineMaxNumber {
	
	private static boolean whichIsBetter(int val1, int val2) {  // return true if val1 is better than val2
		String val1Str = String.valueOf(val1);
		String val2Str = String.valueOf(val2);
		int minsize = val1Str.length() > val2Str.length() ? val2Str.length() : val1Str.length();
		int divider = -1;
		if (minsize == val1Str.length()) {
			divider = (int) Math.pow(10, val2Str.length() - minsize);
			int result = val2 / divider;
			return result >= val1 ? false : true;
		} else {
			divider = (int) Math.pow(10, val1Str.length() - minsize);
			int result = val1 / divider;
			return result >= val2 ? true : false;
		}
	}

	private static String combineMaxNumber(int[] value) {
		boolean allZero = true;
		for (int i = 0; i < value.length; i++) {
			if (value[i] != 0) {
				allZero = false;
			}
		}
		if (allZero) {
			return String.valueOf(0);
		}
		
		for (int i = 0; i < value.length - 1; i++) {  // bubble sorting using whichIsBetter
			for (int j = 0; j < value.length - i - 1; j++) {
				if (whichIsBetter(value[j], value[j + 1])) {
					int tmp = value[j];
					value[j] = value[j + 1];
					value[j + 1] = tmp;
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = value.length - 1; i >= 0; i--) {
			sb.append(value[i]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		int[] value1 = new int[] { 9, 1234, 756, 12, 0 };
		int[] value2 = new int[] { 0, 1 };
		System.out.println(combineMaxNumber(value1));
		System.out.println(combineMaxNumber(value2));
	}
}








