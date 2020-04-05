package com.matrix.arithmetic.number;

import com.matrix.arithmetic.util.AlgoUtil;

/**
 * 埃及分数：把分数分解为分子为1的若干分数之和，取所分分数个数最少，最大分母最小的为最优解。
 * 例如：19/45 = 1/5 + 1/6 + 1/18,
 *      11/999 = 1/216 + 1/296 + 1/333 
 *      
 * @author rayeaster
 *
 */
public class EgyptFraction{
	   
   static final int INF = Integer.MAX_VALUE;
   static final int N = 10;

   static int dep;
   static boolean flag;
   static int[] ans = new int[N];
   static int[] d = new int[N];
   
   public static void main(String[] args){
	   EgyptFraction.Work(11, 999);	   
   }

   private static void dfs(int a, int b, int k){
       if(k == dep + 1){
    	  return;
       }
       if(b % a == 0 && b / a > d[k-1]){//保证分母递增
           d[k] = b / a;
           if(!flag || d[k] < ans[k]){
              for(int i = 0;i < d.length;i++){
            	  ans[i] = d[i];
              }
           }
           flag = true;    	   
           return;
       }
       
       int s = b / a;
       if(s <= d[k-1]){
    	  s = d[k-1] + 1;
       }
       int t = (dep - k + 1) *  b / a;
       if(t > INF / b){
    	  t = INF / b;
       }
       if(flag && t >= ans[dep]){//剪枝，保证最优解，不要超过已经发现最小的最大分母
    	  t = ans[dep] - 1;
       }
       
       for(int i = s;i <= t;i++){
           d[k] = i;
           int m = AlgoUtil.GCD(i * a - b, b * i);
           dfs((i * a - b) / m, b * i / m, k + 1);
       }
   }

   public static void Work(int a, int b){
       d[0] = 1;
       flag = false;
       System.out.println(a + "/" + b + "=");
       
       for(dep = 1;dep <= N;dep++){
           dfs(a,b,1);
           if(flag){
        	   System.out.println();
               System.out.print("1/" + ans[1]);
               for(int i = 2;i <= dep;i++){
            	   System.out.print("+1/" + ans[i]);
               }
               System.out.println();
               break;
           }
       }
   } 

}



