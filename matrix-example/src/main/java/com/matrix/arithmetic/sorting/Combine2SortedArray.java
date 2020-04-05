package com.matrix.arithmetic.sorting;

import com.matrix.arithmetic.util.AlgoUtil;

/**
 * 合并两个有序数组
 * Solution 1 :
 *  新建一个数组然后一个个往里面放。其复杂度为a+b
 * Solution 2 :
 *   从B中用二分法选取插入的连续元素块，直接插入数个元素，
 *   
 *   初始值x=0;b[x]应插入到a[cur]的位置(二分法得到位置），   
 *   那么直到b中最后一个小于a[cur+1]的元素b[y](二分法得到y)共y-x个，
 *   都应依次插入到当前a[cur]的位置，
 *   
 *   从a[cur+1]开始剩余的元素，都应向后移动y-x个位置。
 *   完成后则b[y]之前的元素都已经插入。
 *   
 *   然后x置为y+1,重复以上操作，直至所有b中元素都插入a中
 *   
 *   二分查找时对位置加以限制，从上轮插入结束的位置开始，这样又降低了部分复杂度
 *   复杂度大致为log2(a)+log2(b). 最差时比较和移动了a+b次
 * 
 * @author rayeaster
 *
 */

public class Combine2SortedArray 
{	
    public static int[] MergeList1(int a[],int b[])
    {
        int result[];  
        result = new int[a.length+b.length];
        
        int i=0,j=0,k=0;

        while(i<a.length && j<b.length)
        {
            if(a[i] <= b[j]) 
            {
                result[k++] = a[i++];
            }
            else
            {
                result[k++] = b[j++];
            }
        }
        
        while(i < a.length) 
        {
        	result[k++] = a[i++];
        }
        
        while(j < b.length)
        {
            result[k++] = b[j++];
        }       
        return result;
    }

    //a is the bigger array
	public static int[] MergeList2(int[] a, int[] b) {
		int sIdx = 0;
		int eIdx = a.length - 1;
		
		for(int i = 0;i < b.length && sIdx <= eIdx;i++)
		{
			int bIdx = AlgoUtil.getFirstBiggerIdx(b[i], a, sIdx, eIdx);
			
			int aIdx = AlgoUtil.getFirstBiggerIdx(a[bIdx], b, i, b.length - 1);
			
			int moveSize = aIdx - i;//start from 0
			
			//move elements in a to make room
			for(int s = 0;s < moveSize;s++)
			{
				for(int m = a.length - 1;m >= bIdx && m > 0;m--)
				{			
				    a[m] = a[m - 1];
				}
			}
			
			//move elements in b to a
			for(int j = i, tmp = bIdx;j < i + moveSize && j <= b.length - 1 && tmp <= a.length - 1;)
			{
				a[tmp++] = b[j++];
			}
			
			a[bIdx] = b[i];
			
			if(moveSize > 0)
			{
			   i += moveSize - 1;				
			}
			
			sIdx = bIdx + 1;
			
			//AlgoUtil.print(a);
		}		
			
		return a;
	}
	
	public static void main(String[] args){
    	testSolu1();
    	testSolu2();
    }
	
    public static void testSolu1()
    {
        int a[]={1,2,2,3,5,6,7,7};
        int b[]={1,2,4,5,8,8,9,10,11,12,12,13,14};
        
        int c[]= MergeList1(a,b);
        
        if(c!=null)
        {
        	AlgoUtil.print(c);
        }
    }
    
    public static void testSolu2()
    {
        int b[]={1,2,2,3,6,7};
        
        int a[]= new int[10]; 
        for(int i = 0;i < a.length;i++)
        {
        	a[i] = Integer.MAX_VALUE;
        }
        a[0]=1;a[1]=2;a[2]=4;a[3]=5;
        
        int c[]= MergeList2(a,b);
        
        if(c!=null)
        {
        	AlgoUtil.print(c);
        }
    }
	
}
