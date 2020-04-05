package com.matrix.arithmetic.structure;
import java.util.LinkedList;
import java.util.Queue;


/**
 * use 2 queue to implement stack
 * @author rayeaster
 *
 * @param <T>
 */
public class MyStack<T>{
	
	private Queue<T> q1 = new LinkedList<T>();	
	private Queue<T> q2 = new LinkedList<T>();
	
	private int size = 0; 
	
	private Queue<T> qused = null;
	private Queue<T> qother = null;
	
	public MyStack(){
		qused = q1;
		qother = q2;
	}
	
    public void push(T item){
		qused.add(item);
		size++;
	}
	
	public T pop(){
		while(qused.size() > 1){
		  qother.add(qused.poll());
		}		
		T ret = qused.poll();
		size--;

		Queue<T> tmp = qused;
		qused = qother;
		qother = tmp;
		return ret;
	}
	
	public T peek(){
		while(qused.size() > 1){
			  qother.add(qused.poll());
		}
		T ret = qused.poll();
		qother.add(ret);		
		
		Queue<T> tmp = qused;
		qused = qother;
		qother = tmp;
		return ret;
	}
	
	public int size(){
		return size;
	}
	
	public static void main(String[] args){
		MyStack<String> ms = new MyStack<String>();
		ms.push("1");		
		ms.push("2");
		ms.push("3");
		System.out.println("|head: " + ms.peek() + "|size: " + ms.size() + "|pop: " + ms.pop());
		ms.push("4");
		System.out.println("|head: " + ms.peek() + "|size: " + ms.size() + "|pop: " + ms.pop());
		ms.push("5");
		System.out.println("|head: " + ms.peek() + "|size: " + ms.size() + "|pop: " + ms.pop());
		System.out.println("|head: " + ms.peek() + "|size: " + ms.size() + "|pop: " + ms.pop());
		System.out.println("|head: " + ms.peek() + "|size: " + ms.size() + "|pop: " + ms.pop());
	}
	
}
