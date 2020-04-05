package com.matrix.arithmetic.structure;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import com.matrix.arithmetic.util.AlgoUtil;

/**
 * Use ForkJoin framework to do some recursive search: max number and interested file
 * 
 * @author rayeaster
 *
 */
public class RecursiveSearcher{

    private static final ForkJoinPool pool = new ForkJoinPool(4);

	public static void main(String[] args) { 
	    
		// find maximum number in given array 
		// recursively using divide-and-conquer
		int rsize = 10; 
	    final int[] data = new int[rsize];
	    final Random random = new Random();
	    for (int i = 0; i < data.length; i++) {
	      data[i] = random.nextInt(rsize * 10);
	    }
        AlgoUtil.print(data);
	    final RecursiveMaxSearcher finder = new RecursiveMaxSearcher(data);
	    System.out.println("max number is:" + pool.invoke(finder));
	    
	    // find file with desired suffix 
	 	// recursively using divide-and-conquer
	    final RecursiveFileSearcher fileSearcher = new RecursiveFileSearcher("D:\\code\\workspace\\algo", "java");
	    AlgoUtil.printList(pool.invoke(fileSearcher));
	}
	  
	private static class RecursiveMaxSearcher extends RecursiveTask<Integer> {
		 
		private static final long serialVersionUID = -3415402803986145706L;

		private static final int SEQUENTIAL_THRESHOLD = 5;
		 
		  private final int[] data;
		  private final int start;
		  private final int end;
		 
		  public RecursiveMaxSearcher(int[] data, int start, int end) {
		    this.data = data;
		    this.start = start;
		    this.end = end;
		  }
		 
		  public RecursiveMaxSearcher(int[] data) {
		    this(data, 0, data.length);
		  }
		 
		  @Override
		  protected Integer compute() {
		    final int length = end - start;
		    if (length < SEQUENTIAL_THRESHOLD) {
		      return computeDirectly();
		    }
		    final int split = length / 2;
		    final RecursiveMaxSearcher left = new RecursiveMaxSearcher(data, start, start + split);
		    left.fork();
		    final RecursiveMaxSearcher right = new RecursiveMaxSearcher(data, start + split, end);
		    return Math.max(right.compute(), left.join());
		  }
		 
		  private Integer computeDirectly() {
		    System.out.println(Thread.currentThread() + " computing: " + start + " to " + end);
		    int max = Integer.MIN_VALUE;
		    for (int i = start; i < end; i++) {
		      if (data[i] > max) {
		        max = data[i];
		      }
		    }
		    return max;
		  }
	}
	
	private static class RecursiveFileSearcher extends RecursiveTask<List<String>> {
		
		  private static final long serialVersionUID = -634380603952120842L;
		
          private String suffix;
		  private File dir;
          private List<File> fileList;
          private List<RecursiveFileSearcher> subList;
		 		
          public RecursiveFileSearcher(String targetsuffix) {
        	  suffix = targetsuffix;
			  fileList = new LinkedList<File>();
			  subList = new LinkedList<RecursiveFileSearcher>();
		  }
          
		  public RecursiveFileSearcher(String myDirName,String targetsuffix) {
			  this(targetsuffix);
			  dir = new File(myDirName);
		  }
		  
		  public RecursiveFileSearcher(File myDirPath,String targetsuffix) {
			  this(targetsuffix);
			  dir = myDirPath;
		  }
		 
		  @Override
		  protected List<String> compute() {
		    File[] subs = dir.listFiles();
		    for(File f : subs){
		    	if(f.isDirectory()){
		    		final RecursiveFileSearcher subtask = new RecursiveFileSearcher(f, suffix);
		    		subList.add(subtask);		    		
		    		subtask.fork();
		    	}else{
		    		fileList.add(f);
		    	}
		    }
		    
		    List<String> ret = new LinkedList<String>();
		    for(RecursiveFileSearcher t : subList){
	    		ret.addAll(t.join());
	    	}
		    
		    ret.addAll(computeDirectly());
		    return ret;
		  }
		  
		  private List<String> computeDirectly() {
		    List<String> ret = new LinkedList<String>();
		    for(File f : fileList){
		    	if(f.getName().endsWith(suffix)){
		    	   ret.add(f.getAbsolutePath());
		    	}
		    }
		    return ret;
		  }
	}
    
}
 
