package com.matrix.algorithms.DataStructures.linked;

import java.util.concurrent.TimeUnit;

/**
 * @description: 递归实现单链表
 *		数组：大小固定，需要预先开辟指定大小的空间；不适合插入和删除，适合查询
 *		链表：大小不固定，可以随意增加，但不宜过长。适合插入和删除，不适合查询。
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2020年9月28日 下午8:17:00 
 * @version 1.0.0.1
 */

public class LinkedList<T> {
	private Node root;		// 根节点
	private int currentIndex = 0; // 节点的序号，插入行为需要，每次操作从0开始。
	
	public void add(T data) { 
		if(root == null) {
			root = new Node(data);
		}else {
			root.addNode(data);
		}
	}
	
	public void delete(T data) { 
		if(root == null) return;
		if(root.getData() == data) {	// 如果删除根节点
			root = root.next;  // 根节点关联了元素，则其关联者变成根节点；如果链表只有一个根节点，则会置空。
		}else {
			root.delNode(data);
		}
	}
	
	public boolean update(T old , T new_) {
		if(root == null) return false;
		if(root.getData() == old) {
			root.setData(new_);
			return true;
		}
		return root.updateNode(old, new_);
	}
	
	public T find(T data) { 
		if(root == null) return null;
		if(root.getData() == data) return data;
		return root.findNode(data);
	}
	
	public void list() {	 
		if(root != null) {
			System.out.print(root.getData() + " -> ");		// 由于递归查询，故根节点需要做特殊处理
			root.listAll();
			System.out.println("");
		}
	}
	
	/**
	 * 已有链表：A B C D E F，准备插入新元素K，到B 和 C之间。
	 * K.next = B.next;
	 * B.next = K;  
	 */
	public void insertBefore(int index , T data) {  // 在当前位置向前插入一个元素；向前后者向后都是人为定的，不固定。
		if(index < 0) return;
		currentIndex = 0;
		if(index == currentIndex) {  // 插入的位置是0
			Node node_ = new Node(data);
			node_.next = root;
			root = node_;
		}else {
			root.insertNodeBefore(index, data);
		}
	}
	
	public void insertAfter(int index , T data) {
		if(index < 0) return;
		currentIndex = 0;
		if(index == currentIndex) {  // 插入的位置是0
			Node node_ = new Node(data);
			node_.next = root.next;
			root.next = node_;
		}else {
			root.insertNodeAfter(index, data);
		}
		
	}

	private class Node{
		private T data;
		private Node next;
		public Node(T data) {
			this.data = data;
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		
		public void addNode(T data) {		// 增加一个节点
			if(this.next == null) {  
				this.next = new Node(data);
			}else {
				this.next.addNode(data);  // 递归
			}
		}
		
		public void delNode(T data) {		// 删除一个节点
			if(this.next != null) {			// 如果递归完成都没有匹配到元素，那么删除失败，无影响。
				if(this.next.getData() == data) { // 直至递归到的元素与要删除的元素匹配
					this.next = this.next.next;	// 当前元素A被其所关联的元素覆盖。
				}else {
					this.next.delNode(data);  
				}
			}
		}
		
		public boolean updateNode(T old , T new_) {		// 修改一个节点
			if(this.next != null) {
				if(this.next.data == old) {
					this.next.data = new_;
					return true;
				}
				return this.next.updateNode(old, new_);
			}
			return false;
		}
		
		public T findNode(T data) {		// 查找一个节点
			if(this.next != null) {
				if(this.next.data == data) {
					return this.next.data;
				}
				return this.next.findNode(data);
			}
			return null;
		}
		
		public void listAll() {			// 输出所有节点
			if(this.next != null) {
				System.out.print(this.next.getData() + " -> ");   
				this.next.listAll();
			}
		}
		
		public void insertNodeBefore(int index , T data) {  // 在当前索引前，插入一个节点
			if(this.next == null) {
				System.out.println("索引越界：" + index + ";  insert before 插入失败 this.next == null");
				return;
			}
			currentIndex ++;
			if(index == currentIndex) {
				Node node_ = new Node(data);
				node_.next = this.next;		// 在递归调用中，【this】指向的是上一个对象，不是本方法中的对象，此处需要注意！！
				this.next = node_;
			}else {
				this.next.insertNodeBefore(index, data);
			}
		}
		
		public void insertNodeAfter(int index , T data) {  // 在当前索引后，插入一个节点
			if(this.next == null) {
				System.out.println("索引越界：" + index + ";  insert after 插入失败 this.next == null");
				return;
			}
			currentIndex ++;
			if(index == currentIndex) {
				Node node_ = new Node(data);
				node_.next = this.next.next;
				this.next.next = node_;
			}else {
				this.next.insertNodeAfter(index, data);
			}
		}
	}
}







/**
 	
	public static void main(String[] args) {
		LinkedList<Integer> link = new LinkedList<Integer>();
		System.out.println("========添加：5个元素=========");
		link.add(1);
		link.add(2);
		link.add(3);
		link.add(4);
		link.add(5);
		link.list();
		System.out.println("========删除：第3个元素=========");
		link.delete(3);
		link.list();
		System.out.println("========修改：将5 改成6=========");
		link.update(5, 6);
		link.list();
		System.out.println("========查询：6=========");
		Integer find = link.find(6);
		System.out.println("find target key = " + find);
		
		System.out.println("========插入 insert before：索引=3 插入值：7=========");
		link.insertBefore(3, 7);  // 计数从0开始
		link.list();
		
		System.out.println("========插入 insert before：索引=5 插入值：8=========");
		link.insertBefore(5, 8); 
		link.list();
		
		System.err.println("========插入 insert after：索引=4 插入值：9=========");
		link.insertAfter(4, 9);   
		link.list();
		
		System.err.println("========插入 insert after：索引=6 插入值：10=========");
		link.insertAfter(6, 10);   
		link.list();
		
	}

 */























