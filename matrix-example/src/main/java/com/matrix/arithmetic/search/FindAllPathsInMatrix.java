package com.matrix.arithmetic.search;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.matrix.arithmetic.util.AlgoUtil;

/**
 * 找出从矩阵第一行第一列到最后一行最后一列的所有路径。
 * 基本思想：动态规划
 * 
 * @author rayeaster
 *
 */
public class FindAllPathsInMatrix{
		
	public static void main(String[] args){
		String[][] a = 
	        {
	        		{"a00", "a01", "a02"},
	        		{"a10", "a11", "a12"},
	        		{"a20", "a21", "a22"}
	        };		
		
		List<List<String>> ret = findAllPath(a);
		AlgoUtil.printList(ret);
	}
	
	@SuppressWarnings("unchecked")
	public static List<List<String>> findAllPath(String[][] a){
		
		 //init matrix for calculation
		 Map<String, List<List<String>>> paths = new HashMap<>();
		 for(String[] ss : a){
			 for(String s : ss){
				 paths.put(s, new LinkedList<List<String>>());
			 }
		 }
		 
		 int length = a.length;
		 for(int i = length - 1;i >= 0;i--){
			 for(int j = length - 1;j >= 0;j--){				 
				 if((i == length - 1 && j == length - 1)){// the last step
				   List<String> newl = new LinkedList<String>();
				   newl.add(0, a[i][j]);				   
				   paths.get(a[i][j]).add(newl);
				 }else{
				   //f(si,sj) = f(si+1, sj) + f(si, sj+1)
				   List<List<String>> sij = paths.get(a[i][j]);
				   
				   List<List<String>> sip1 = 
						   i == length - 1?Collections.EMPTY_LIST : paths.get(a[i + 1][j]);
				   for(List<String> p : sip1){
					   List<String> pc = new LinkedList<String>();
					   pc.addAll(p);
					   pc.add(0, a[i][j]);
					   sij.add(pc);
				   }
				   List<List<String>> sjp1 = 
						   j == length - 1?Collections.EMPTY_LIST : paths.get(a[i][j + 1]);
				   for(List<String> p : sjp1){
					   List<String> pc = new LinkedList<String>();
					   pc.addAll(p);
					   pc.add(0, a[i][j]);
					   sij.add(pc);
				   }				   
				 }				 
			 }
		 }
		 
		 List<List<String>> ret = paths.get(a[0][0]);
		 return ret;
	}
	
}