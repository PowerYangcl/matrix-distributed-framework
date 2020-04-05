package com.matrix.arithmetic.string;

import java.util.Arrays;


/**
 * 最长回文字串
 * 
 * 返回给定字符串的最长回文字串, manacher算法，采用动态规划思想
 * 每次根据当前检查的字符array[i]的回文半径radius[i]，
 * 决定最多到后续字符[i+1, radius[i] + i]范围的新回文半径
 * 
 * @author rayeaster
 *
 */
public class HuiwenString 
{
	
	/** 
     * 返回例如 #a#c#b#c#a#a#c#b#c#d#形式的字符串数组
     *  
     * @param s 
     * @return 
     */  
    public static char[] init(String s)  
    {  
        char[] str = new char[s.length() * 2 + 1];  
  
        int i = 0;  
        for (; i < s.length(); i++)  
        {  
            str[2 * i] = '#';  
            str[2 * i + 1] = s.charAt(i);  
        }  
        str[2 * i] = '#';  
  
        return str;  
    }  
  
    /** 
     *  
     *  
     * @param str 
     */  
    public static void manacher(char[] s)  
    {  
        int rad[] = new int[s.length];  
  
        int i = 1, j = 0;  //i starts from 1
  
        // 记录最长的回文串的长度  
        int maxLen = 0;  
        while (i < s.length)  
        {  
        	// 扫描得出rad值   
            while (i - j - 1 > -1 
            	   && i + j + 1 < s.length  
                   && s[i - j - 1] == s[i + j + 1])  
            {
                j++;
            }
            rad[i] = j;  
  
            maxLen = maxLen > j ? maxLen : j;  
  
            int k = 1;  
            while (k <= rad[i] && rad[i - k] != rad[i] - k)  
            {  
                rad[i + k] = Math.min(rad[i - k], rad[i] - k);  
                k++;  
            }   
            //update the next search center
            i = i + k;  
            //update the next search radius, if always 0, duplicate work is done
            j = Math.max(j - k, 0);  
        }  
  
        System.out.println(Arrays.toString(rad));  
        System.out.println("最长回文串长度： " + maxLen);  
    }  
		
	public static void main(String[] args)
	{
		String s = "acbcaacbcd";  
        char[] str = init(s);  
        System.out.println(Arrays.toString(str));  
        manacher(str);
    }
}
