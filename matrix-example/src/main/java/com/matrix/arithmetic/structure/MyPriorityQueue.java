package com.matrix.arithmetic.structure;
import java.util.Comparator;
import java.util.Stack;

/**
 * Use 2 stack to implement queue
 * @author rayeaster
 *
 * @param <T>
 */
public class MyPriorityQueue<T>{
	
	private Stack<T> s1 = new Stack<T>();	
	private Stack<T> s2 = new Stack<T>();
	
	private int size = 0; 
	
	private Stack<T> sused = null;
	private Stack<T> sother = null;
	
	private Comparator<T> comp = null; 
	
	public MyPriorityQueue(Comparator<T> comparator){
		sused = s1;
		sother = s2;
		comp = comparator;
	}
	
    public void push(T item){
    	if(size > 0){
    		T first = sused.peek();
        	while(first != null && comp.compare(item, first) > 0){
        		sother.push(sused.pop());        		
        		if(sused.size() <= 0){
        		   break;
        		}else{
        		   first = sused.peek();
        		}
        	}
        	while(sother.size() > 0){
        		sused.push(sother.pop());
        	}
    	}else{
    		sused.push(item);
    	}		
		size++;
	}
	
	public T pop(){
		while(sused.size() > 1){
		  sother.push(sused.pop());
		}		
		T ret = sused.pop();
		size--;
		
		while(sother.size() > 0){
		  sused.push(sother.pop());
		}
		return ret;
	}
	
	public T peek(){
		while(sused.size() > 1){
			  sother.push(sused.pop());
		}		
		T ret = sused.pop();
	    sother.push(ret);
	    
	    while(sother.size() > 0){
			  sused.push(sother.pop());
		}
		return ret;
	}
	
	public int size(){
		return size;
	}
	
	private static class MyComparator<T> implements Comparator<T>{

		@Override
		public int compare(T o1, T o2) {
			if(o1 instanceof String && o2 instanceof String){
			   Integer i1 = Integer.parseInt((String) o1);
			   Integer i2 = Integer.parseInt((String) o2);
			   if(i1.intValue() == i2.intValue()){
				  return 0;
			   }else{
				  return i1 > i2? 1 : -1; 
			   }
			}
			return -1;
		}
	}
	
	public static void main(String[] args){
		MyPriorityQueue<String> tq = new MyPriorityQueue<String>(new MyComparator<String>());
		tq.push("1");		
		tq.push("2");
		tq.push("3");
		System.out.println("|head: " + tq.peek() + "|size: " + tq.size() + "|pop: " + tq.pop());
		tq.push("4");
		System.out.println("|head: " + tq.peek() + "|size: " + tq.size() + "|pop: " + tq.pop());
		tq.push("5");
		System.out.println("|head: " + tq.peek() + "|size: " + tq.size() + "|pop: " + tq.pop());
		System.out.println("|head: " + tq.peek() + "|size: " + tq.size() + "|pop: " + tq.pop());
		System.out.println("|head: " + tq.peek() + "|size: " + tq.size() + "|pop: " + tq.pop());		
	}
	
}
