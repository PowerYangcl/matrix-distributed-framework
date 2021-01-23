package com.matrix.sxt.e17;

public class Palindrome {
	public static String longestPalindrome(String s) {
		int length = s.length();
		if (length == 0 || length == 1)
			return s;
		int[][] dp = new int[length][length]; // 定义二位数组存储值，dp值为1表示true，为0表示false
		int start = 0; 	// 回文串的开始位置
		int max = 1; 		// 回文串的最大长度
		for (int i = 0; i < length; i++) { // 初始化状态
			dp[i][i] = 1;
			if (i < length - 1 && s.charAt(i) == s.charAt(i + 1)) {
				dp[i][i + 1] = 1;
				start = i;
				max = 2;
			}
		}
		for (int n = 3; n <= length; n++) { // n表示检索的子串长度，等于3表示先检索长度为3的子串
			for (int i = 0; i + n - 1 < length; i++) {
				int j = n + i - 1; // 终止字符位置
				if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1] == 1) { // 状态转移
					dp[i][j] = 1; // 是一，不是字母l
					start = i;
					max = n;
				}
			}
		}
		return s.substring(start, start + max); 		// 获取最长回文子串
	}

	public static void main(String[] args) {
		String str = "cbabcfcbabcddddd";
		System.out.println(str);
		System.out.println(longestPalindrome(str));
	}
}



















