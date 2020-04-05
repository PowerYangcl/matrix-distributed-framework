package com.matrix.arithmetic.search;

/**
 *  在有序的N*N矩阵中，查找给定数字。
 *  
 * 	n-n matrix (each column is increasing from top to bottom, same with every row from left to right),
 *  find a number using O(n) complexity, 
 *  start from upper-right most corner
 *  
 * @author rayeaster
 *
 */

public class SearchInSortedMatrix{
	
	public static void main(String[] args){
		String val = "ab我看abc";
		char[] arr = val.toCharArray();
        int[][] a = 
        {
        		{1, 5, 13,16},
        		{3, 7, 15,18},
        		{9, 10,21,22},
        		{12,14,33,40}
        };
        System.out.println(findX(a, 12));        
        
        //piggyback, recursive calculation of string length
        System.out.println(strlen(arr, 0));
	}
	

	public static String findX(int[][] a, int x){
		int n = a.length;
		int si = 0;
		int sj = n - 1;

		while(a[si][sj] != x && si < n && sj > 0){
			if(a[si][sj] > x){
				sj--;
			}else if(a[si][sj] < x){
				si++;
			}else{
				break; 
			}
		}
		
		if(a[si][sj] == x){
		   return "success:" + si + "_" + sj;
		}else{
		   return "not found:" + si + "_" + sj;
		}
	}
	
	public static int strlen(char[] s, int sum){
	   try{
	     if(s[sum] != 0x00) {
		    return strlen(s, sum + 1);  
	     }else{
	    	return -1;//invalid char 0x00
	     }
	   }catch(Exception e){
		 return sum;
	   }	   
	}
	
}
