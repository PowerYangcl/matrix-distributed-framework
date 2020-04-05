package com.matrix.arithmetic.number;

import java.util.ArrayList;
import java.util.List;

/** 
* 世界上有10种人，一种懂二进制，一种不懂。那么你知道两个int32整数m和n的二进制表达，有多少个位(bit)不同么？ 
* 输入例子:
* 1999 2299
* 输出例子:
* 7
*/ 
public class BinaryCommonDigit {

	/**
	 * 二进制字符串转字符串集合
	 * @param binaryString 二进制字符串 
	 * @return List<String>
	 */
	private List<String> convertList(String binaryString) {

		int intStringLength = binaryString.length();
		
		List<String> intStringList = new ArrayList<String>();

		for (int i = 0; i < intStringLength; i++) {
			String intChar = binaryString.substring(i, i + 1);
			intStringList.add(intChar);
		}
		
		//二进制补位,统一位数32
		if (intStringLength < 32) {
			int temp = 32 - intStringLength;
			List<String> tempList = new ArrayList<String>();
			for(int i =0;i<temp;i++){
				tempList.add("0");
			}
			tempList.addAll(intStringList);
			intStringList = tempList;
		}else if (intStringLength > 32) {
			int temp = intStringLength -32;
			intStringList = intStringList.subList(temp, intStringLength);
		}

		return intStringList;
	}


	/**
	 * 两个int32整数的二进制表达位不同数
	 * @param mList 第一个集合
	 * @param nList 第二个集合
	 * @return int
	 */
	private int comparisonNumber(List<String> mList, List<String> nList) {
		int finalNumber = 0;
		
		for (int i = 0; i < mList.size(); i++) {
			String tempMString = mList.get(i);
			String tempNString = nList.get(i);
			if (!(tempMString.equals(tempNString))) {
				finalNumber += 1;
			}
		}

		return finalNumber;
	}

	/**
	 * 获得两个整形二进制表达位数不同的数量
	 * @param m 整数m
	 * @param n 整数n
	 * @return 整数
	 */
	public int countBitDiff(int m, int n) {

		// 转二进制字符串
		String mString = Integer.toBinaryString(m);
		String nString = Integer.toBinaryString(n);
		
		// 转二进制集合
		List<String> mList = convertList(mString);
		List<String> nList = convertList(nString);

		return comparisonNumber(mList, nList);
	}
	
	public static void main(String[] args) {
		BinaryCommonDigit solution = new BinaryCommonDigit();
		System.out.println(solution.countBitDiff(16807,282475249));
	}
}