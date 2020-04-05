package com.matrix.arithmetic.number;

/**
 * @description: 阶乘 N!
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2020年4月1日 下午9:51:57
 * @version 1.0.0.1
 */
public class JieCheng {

	public int doFactorial(int n) {
		if (n < 0) {
			return -1;// 传入的数据不合法
		}
		if (n == 0) {
			return 1;
		} else if (n == 1) {// 递归结束的条件
			return 1;
		} else {
			return n * doFactorial(n - 1);			// 递归算法
		}
	}

	public int doFactorial2(int n) {			// 非递归
		int result = 1;
		if (n < 0) {
			return -1;// 返回-1，说明传入数据不合法
		}
		if (n == 0) {
			return 1;
		}
		for (int i = 1; i <= n; i++) {
			result *= i;
		}
		return result;
	}
}
