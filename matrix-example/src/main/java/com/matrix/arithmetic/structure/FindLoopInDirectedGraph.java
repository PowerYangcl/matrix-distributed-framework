package com.matrix.arithmetic.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Use DFS to find all loop path in directed graph
 * example directed graph and result:
 *    Loop:0 8 5 1 3 7 2 
 *    Loop:0 8 5 1 3 7 6 
 *    Loop:0 8 5 1 4 7 2 
 *    Loop:0 8 5 1 4 7 6
 * 
 *        ------> 8-------------
 *        |                     |
 *        |   -----> 4 ---->7 --}--
 *        |   |             ^   | |
 *        |   |             |   | |
 *        0   1----> 3 ------   | |
 *        ^   ^                 | |
 *        |   |                 | |
 *        |   -------5 <--------- |
 *        ----2 <-----------------|                
 *        |                       |
 *        -------------- 6 <------    
 *            
 * @author rayeaster
 */
public class FindLoopInDirectedGraph {
    static private final int POINT_NUM = 9;

 	static Set<Integer> nodeSet = new HashSet<Integer>();
 	static{
 		for(int i = 0;i < POINT_NUM;i++){
     		nodeSet.add(i);
     	}
 	}
 	static Set<Integer> visited = new HashSet<Integer>();
 	
    static private int[][] e = {
                 	        //use sparse matrix to denote graph
     
     {0,0,0,0,0,0,0,0,1},
     {0,0,0,1,1,0,0,0,0},
     {1,0,0,0,0,0,0,0,0},
     {0,0,0,0,0,0,0,1,0},
     {0,0,0,0,0,0,0,1,0},
     {0,1,0,0,0,0,0,0,0},
     {1,0,0,0,0,0,0,0,0},
     {0,0,1,0,0,0,1,0,0},
     {0,0,0,0,0,1,0,0,0}};
     
     /*
     {0,0,0,0,0,0,0,0,0},
   	 {1,0,0,0,0,0,0,0,0},
   	 {0,1,0,0,0,0,0,0,0},
   	 {0,0,1,0,0,0,1,0,0},
   	 {0,0,0,1,0,0,0,0,0},
   	 {0,0,0,0,1,0,0,0,0},
   	 {0,0,0,0,0,1,0,0,0},
   	 {0,0,0,0,0,0,1,0,0},
   	 {0,0,0,0,0,0,0,1,0}};//another example
     */
    static ArrayList<Integer> trace = new ArrayList<Integer>();
    static boolean hasLoop = false;
    
    public static void main(String[] args) {
     	
     	while(!nodeSet.isEmpty()){
     		Iterator<Integer> iter = nodeSet.iterator();
            findCycle(iter.next());
     	}
     	
        if(!hasLoop){      	
            System.out.println("No Loop.");
        }
    }
    
    static void findCycle(int v){
       int j = trace.indexOf(v);
       if(j != -1){
    	        hasLoop = true;
                System.out.print("Loop:");
                while(j < trace.size())
                {
                    System.out.print(trace.get(j) + " ");
                    j++;
                }
                return;
        }
        trace.add(v);
        //update
        visited.add(v);
        nodeSet.remove(v);
         
        for(int i = 0;i < POINT_NUM;i++){
            if(e[v][i] == 1){
                findCycle(i);
            }
        }
        trace.remove(trace.size() - 1);//remove last-added(leaf) node
    }
}
