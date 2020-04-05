package com.matrix.arithmetic.util;

import java.util.List;

/**
 * 一些常用的工具函数
 * 
 * @author rayeaster
 *
 */
public class AlgoUtil 
{    
	// 打印函数
    public static void print(int b[])
    {
         for(int i=0; i< b.length; i++)
         {
             System.out.print(b[i] + (i%10 == 9 ? "\n":"\t"));
         }
         System.out.println();
    }
    
    public static void printList(List l)
    {
         for(int i=0; i< l.size(); i++)
         {
        	 Object o = l.get(i);
        	 if(o instanceof List)
        	 {
        		 printList((List) o); 
        	 }
        	 else
        	 {
                 System.out.print(o + (i%10 == 9 ? "\n":"\t"));
        	 }
         }
         System.out.println();
    }
    
    // return greatest common divisor
    public static int GCD(int a, int b){
    	return b != 0 ? GCD(b, a%b):a;  
    }
    
    //return index in tArray for val where tArray[index] is
    //the first element bigger than val in tArray 
    public static int getFirstBiggerIdx(int val, int[] tArray, int sIdx, int eIdx)
    {
    	int ret = -1;
    	
    	if(sIdx > eIdx)
    	{
    	   return ret;    	   
    	}
    	
    	if(sIdx < 0)
    	{
    	   return 0;
    	}
    	if(eIdx > tArray.length - 1)
    	{
    	   return tArray.length - 1;
    	}
    	
    	if(val < tArray[sIdx])
    	{
    	   return sIdx;
    	}
    	if(val > tArray[eIdx])
    	{
    	   return eIdx;
    	}
    	
    	while(sIdx < eIdx - 1)
    	{
        	ret = (eIdx + sIdx) / 2;
        	
    		if(val <= tArray[ret])
    		{
    		   eIdx = ret;
    		}
    		else
    		{
    		   sIdx = ret;    		   
    		}
    	}
    	
    	ret = eIdx;
    	
    	return ret;
    }
}