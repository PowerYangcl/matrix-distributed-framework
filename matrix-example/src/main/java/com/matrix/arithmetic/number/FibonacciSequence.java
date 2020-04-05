package com.matrix.arithmetic.number;

/**
 * @description: 斐波那契数列
 * 
 * 斐波那契数列（Fibonacci sequence），又称黄金分割数列、因数学家列昂纳多·斐波那契（Leonardoda Fibonacci）以兔子繁殖为例子而引入，
 * 故又称为“兔子数列”，指的是这样一个数列：1、1、2、3、5、8、13、21、34、……在数学上，斐波那契数列以如下被以递推的方法定义
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2020年4月1日 下午9:54:06 
 * @version 1.0.0.1
 */
public class FibonacciSequence {
		// 建立一个函数，用于计算数列中的每一项
		public static int fib(int num) {
			// 判断：是否是第一个数和第二个数
			if(num == 1 || num == 2) {
				return 1;
			}else {
				// 循环调用本函数
				return fib(num - 2) + fib(num - 1);
			}
		}
		
		public static void main(String[] args) {
			// 建立一个for循环，用于打印第一个至第十个数字
			for(int i = 1;i <= 10;i++) {
				// 调用函数进行打印
				System.out.print(fib(i) + "\t");
			}	
		}

}
